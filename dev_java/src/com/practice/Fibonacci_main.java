package com.practice;

import java.util.Scanner;

public class Fibonacci_main {

	public static void main(String[] args) {
		Fibonacci_numbers fn = new Fibonacci_numbers();
		
		int num;//사용자가 입력하는 값이 담길 변수
		Scanner scan = new Scanner(System.in);
		
		System.out.println("구하려는 항의 갯수를 입력하세요");
		
		num = scan.nextInt();//사용자의 입력값 초기화
		
		System.out.println(num+"항까지의 피보나치수열은");
		System.out.println(fn.fibonacci(num));//출력
		System.out.println("입니다.");
		
	}
}
