package com.ch2;

public class PNCSimul {

	public static void main(String[] args) {
		Parent p = new Child();

		p.print();
		System.out.println("자식의 나이 : " + p.age);
		System.out.println("자식의 이름 : " + p.name);

	}

}
