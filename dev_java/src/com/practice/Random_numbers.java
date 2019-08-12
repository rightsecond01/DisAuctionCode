package com.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Random_numbers {	

	public static void main(String[] args) throws IOException {

		StringBuilder print = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				print.append((int) (Math.random() * 10));
			}
			print.append("\n");
		} // 10개씩 입력
		System.out.println(print);// 출력

		String randomNumbers = print.toString();
		StringReader sr = new StringReader(randomNumbers);
		BufferedReader br = new BufferedReader(sr);// 버퍼

		int[] count = new int[10];//빈도수를 담을 배열

		String thisLine = null;
		

		while ((thisLine = br.readLine()) != null) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < thisLine.length(); j++) {
					if (i == thisLine.charAt(j) - '0') {
						count[i] = count[i] + 1;
					}
				}
			}
		}//숫자의 빈도수를 카운트 하는 과정

		for (int i = 0; i < 10; i++) {
			System.out.println("숫자" + i + "의 빈도수는 " + count[i] + "입니다.");
		}// 빈도수 출력 

	}

}
