package com.practice;

public class Minus_random_numbers {

	public static void main(String[] args) {

		int[] arrays = new int[10];

		for (int i = 0; i < arrays.length; i++) {
			arrays[i] = (int) ((Math.random() - 0.5) * 10);
		}

		int sum = 0;
		for (int i = 0; i < arrays.length; i++) {
			sum = sum + arrays[i];
		}
		for(int i=0;i<arrays.length;i++) {
			System.out.print(arrays[i] + " ");
		}
		System.out.println("생성된 배열의 양수,음수의 합은" + sum + "입니다");

	}

}
