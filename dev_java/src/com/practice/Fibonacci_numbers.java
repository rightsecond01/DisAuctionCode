package com.practice;

import java.util.ArrayList;

public class Fibonacci_numbers {

	public ArrayList<Integer> fibonacci(int n) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		int a1 = 1;
		int a2 = 1;
		
		numbers.add(a1);
		numbers.add(a2);
		
		int an;
		for(int i=0;i<n-2;i++) {
			an = numbers.get(i) + numbers.get(i+1);
			numbers.add(an);
		}
		return numbers;
				
	}

}
