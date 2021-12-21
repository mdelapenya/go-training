package com.github.simonadonea.quiz.model;

import java.util.Scanner;

public class Problem {
	private String question;
	private String answer;
	private int index;

	public Problem(String question, String answer, int index) {
		this.question = question;
		this.answer = answer;
		this.index = index;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean ask(Scanner scanner) {
		System.out.format("Question %d: %s\n", this.index + 1, this.question);

		System.out.print("Answer: ");

		String userAnswer = scanner.next();

		return userAnswer.equals(this.answer);
	}
}
