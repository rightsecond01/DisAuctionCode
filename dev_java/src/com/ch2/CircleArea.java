package com.ch2;

public class CircleArea extends Area {
	
	public void area(double radius) {
		double width = radius*(3.14);
		double height = radius;
		
		super.area(width, height);
	}

}
