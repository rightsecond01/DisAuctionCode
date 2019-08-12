package com.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Random_numbers2 {

	public int[] frequency(BufferedReader bufferedreader) throws IOException {//빈도수 카운트 메소드
		int[] count = new int[10];// 빈도수를 담을 배열

		String thisLine = null;

		while ((thisLine = bufferedreader.readLine()) != null) {//한줄씩 불러와서 각 숫자를 0~9까지 확인후 해당 숫자의 빈도수를 카운트
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < thisLine.length(); j++) {
					if (i == thisLine.charAt(j) - '0') {
						count[i] = count[i] + 1;
					}
				}
			}
		} // 숫자의 빈도수를 카운트 하는 과정

		return count;
	}

	public String randomString() {// 난수 10개씩 문자열 만들기
		StringBuilder print = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				print.append((int) (Math.random() * 10));
			}
			print.append("\n");
		} // 10개씩 입력

		String randomNumbers = print.toString();

		return randomNumbers;
	}

	public static void main(String[] args) throws IOException {

		Random_numbers2 rn2 = new Random_numbers2();

		String result = rn2.randomString();//난수문자열생성 메소드 호출
		System.out.println(result);// 난수문자열 출력

		StringReader sr = new StringReader(result);
		BufferedReader br = new BufferedReader(sr);// 버퍼생성

		int[] cnt = new int[10];//
		cnt = rn2.frequency(br);

		for (int i = 0; i < 10; i++) {
			System.out.println("숫자" + i + "의 빈도수는 " + cnt[i] + "입니다.");
		} // 빈도수 출력

	}

}
