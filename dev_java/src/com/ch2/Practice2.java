package com.ch2;

public class Practice2 {
	
	public static void main(String[] args) {
		String a = "auctionRoom||리니지";
		String index = "||";
		String asub = a.substring(a.indexOf(index)+index.length(), a.length());
		System.out.println(asub);
	}
}