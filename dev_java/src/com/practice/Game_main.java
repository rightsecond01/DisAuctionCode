package com.practice;

import java.util.Scanner;

public class Game_main {

	public static void main(String[] args) {
		Game con = new Game();
		
		int randomNum = (int)(Math.random()*10);
		int i = 0;
		
		System.out.println("숫자 맞추기 게임입니다.");
		do {
			int num;//사용자가 입력하는 값이 담길 변수
			Scanner scan = new Scanner(System.in);
			System.out.println("정답을 입력하세요(0~9)");
			num = scan.nextInt();//사용자의 입력값 초기화
			
			String result = con.game(num,randomNum);
			if(result.equals("정답입니다.")) {
				System.out.println(result);
				return;
			} else {
				if(i==4) {
					System.out.println("실패!");
				}else {
					System.out.println(result);
					System.out.println("남은기회 " + (4-i) + "번");
				}

			}
			
			i++;
			}while(i<5);	
		

	}

}
