package com.baseballgame;

public class BaseballGameLogic {

	int[] answer, userEnterAnswer;

	public BaseballGameLogic(int[] answer, int[] userEnterAnswer) {
		this.answer = answer;
		this.userEnterAnswer = userEnterAnswer;
	}

	public String strikeBall() {
		String scoring;
		int strike = 0;
		int ball = 0;

		for (int i = 0; i < answer.length; i++) {
			for (int j = 0; j < userEnterAnswer.length; j++) {
				if (answer[i] == userEnterAnswer[j]) {
					if (i == j) {
						strike++;
					} else {
						ball++;
					}
				}
			}
		}
		scoring = strike + "S" + ball + "B";

		return scoring;
	}

}
