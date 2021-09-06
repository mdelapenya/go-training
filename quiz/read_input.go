package main

import (
	"encoding/csv"
	"fmt"
	"io"
	"log"
	"os"
	"time"
)

func main() {
	q := Quiz{}
	q.LoadFile("resources/quiz.csv")

	q.StartQuiz()
}

func (q *Quiz) LoadFile(fileName string) {
	file, err := os.Open(fileName)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	lines, err := csv.NewReader(file).ReadAll()
	if err != nil {
		log.Fatal(err)
	}

	q.Problems = make([]*Problem, len(lines))
	for i, line := range lines {
		q.Problems[i] = new(Problem)
		q.Problems[i].question = line[0]
		q.Problems[i].answer = line[1]
		q.Problems[i].index = i
	}
}

func (q *Quiz) StartQuiz() {
	responses := make(chan string)
	correctAnswers := 0

	fmt.Println("Quiz is starting!")
	for _, problem := range q.Problems {
		go askQuestion(*problem, responses)

		select {
		case userAnswer, ok := <-responses:
			if !ok {
				fmt.Println("ERROR: Expected answer!")
				log.Fatalln(ok)
			}
			if userAnswer == problem.answer {
				correctAnswers++
			}
		case <-time.After(30 * time.Second):
			fmt.Println("\nTIME ELAPSED!")
		}

	}
	fmt.Printf("Number of correct answers: %d from %d possible", correctAnswers, len(q.Problems))
}

func askQuestion(problem Problem, replies chan string) {
	fmt.Printf("Question %d: %s\n", problem.index+1, problem.question)
	fmt.Printf("Answer: ")

	userAnswer := ""
	n, err := fmt.Scanln(&userAnswer)
	if n == 0 {
		userAnswer = ""
	}
	if err != nil {
		close(replies)
		if err == io.EOF {
			return
		}
		log.Fatalln(err)
	}
	replies <- userAnswer
}

type Quiz struct {
	Problems []*Problem
}
type Problem struct {
	question, answer string
	index            int
}
