package com.practice;

import java.util.Arrays;

public class Random_array {
	
	public static void main(String[] args) {
		int[] array = new int[10];
		for(int i=0;i<array.length;i++) {
			array[i] = (int)(Math.random() * 100);
		}//난수 배열 생성
		
	   //Arrays.parallelSort(array); 이걸 써도 오름차순이 되나, 이걸 의도한게 아닐꺼라 판단하고 다른방법 사용
		
		for(int i=0;i<array.length;i++) {
			for(int j=i+1;j<array.length;j++) {
				if(array[i]>array[j]) {
					int temp =array[i];
					array[i]=array[j];
					array[j]=temp;
				}
			}
		}
	   
	   for(int i=0;i<array.length;i++) {
		   System.out.println(array[i]);
	   }
	}
	
}
