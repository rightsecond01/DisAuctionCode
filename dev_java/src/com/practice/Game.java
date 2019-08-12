package com.practice;

public class Game {
	
	public String game(int answer, int n) {
						
		if(answer>=n) {
			if(n==answer) {
				return "정답입니다.";
			} else {
				return "Down";
			}
		} else {
			return "Up";
		}
		
	}

}
