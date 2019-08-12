package com.ch2;

public class Sonata {
	
	int wheelNum = 4;
	int speed = 0;
	String carColor = "빨강";
	void move(int i) {
		speed = speed+1;
		System.out.println("지역변수 i는" + i);
	}
	
	
	public static void main(String[] args) {
		
		Sonata myCar = new Sonata();
		myCar.move(50);
		System.out.println(myCar.wheelNum);
		

	}

}
