package com.practice;

public class Fibonacci_numbers_other {
	
	public StringBuffer Finobacci_numbers_other(int n){
			
		int a = 1;
		int b = 1;
		int c;
		StringBuffer result = new StringBuffer();
		
		result.append(a + ",");
		result.append(b + ",");
		for(int i = 0;i < n-2;i++) {
			c = a + b;
			result.append(c + ",");
			b = a;
			a = c;
		}
		return result;
	
	}

}
