package com.ch2;

public class Sub extends Sup{
	
	int age = 22;
	public Sub() {
		super();
	}
	
	public void methodB() {
		System.out.println("B메소드 호출");
	}
	
	public void print() {
		System.out.println("자식클래스" + age);
	}

}
