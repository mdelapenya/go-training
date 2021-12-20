package com.github.simonadonea.quiz.model;

import java.util.Scanner;

public class Quiz {
	private Scanner scanner;
	private Problem[] problems;

	public Quiz(Scanner scanner, Problem[] problems) {
		this.scanner = scanner;
		this.problems = problems;
	}

	public Problem[] getProblems() {
		return problems;
	}

	public void start() {
		int correctAnswers = 0;

		System.out.println("Quiz is starting!");
		for (Problem problem : this.problems) {
			boolean isCorrect = problem.ask(this.scanner);

			if (isCorrect) {
				correctAnswers++;
			}
		}

		System.out.format("Number of correct answers: %d from %d possible\n", correctAnswers, this.problems.length);
	}
}
