package com.practice;

import java.util.Scanner;

public class Fibonacci_main_other {

	public static void main(String[] args) {
		Fibonacci_numbers_other fno = new Fibonacci_numbers_other();
		
		int num;//사용자의 입력값이 담길 변수
		Scanner scan = new Scanner(System.in);
		System.out.println("구하려는 피보나치 수열의 항의 갯수를 입력하세요.");
		num = scan.nextInt();
		
		StringBuffer sb = fno.Finobacci_numbers_other(num);
		String numbers = sb.substring(0);
		System.out.println(numbers.substring(0,numbers.length()-1));		
	}

}
