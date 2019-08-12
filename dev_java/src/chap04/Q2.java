package chap04;

import java.io.Serializable;
import java.lang.reflect.Array;

public class Q2<E> implements Serializable {
	private int max;
	private int ptr;
	private E [] stk;
	
	/*
	 * public class EmptyIntStackException<E> extends RuntimeException{ public
	 * EmptyIntStackException(){
	 * 
	 * } }
	 */
	
	/*
	 * public class OverflowIntStackException extends RuntimeException{ public
	 * OverflowIntStackException() {
	 * 
	 * } }
	 */
	
	public Q2(Class<E> clazz,int capacity) {
		ptr = 0;
		max = capacity;
		try {
			stk = (E [])Array.newInstance(clazz, max);
		} catch (OutOfMemoryError e) {
			max = 0;
		}
	}
	
	public E push(E clazz) throws Exception{
		if(ptr>=max) {
			
		}
		stk[ptr++] = clazz;
		return clazz;
	}
	
	public E pop() throws Exception{
		if(ptr<=0) {
			
		}
		E temp;
		temp = stk[ptr-1];
		stk[ptr-1] = null;
		ptr--;
		return temp;
	}
	public E peek() throws Exception{
		if(ptr<=0) {
			
		}
			
		return stk[ptr -1];
	}
	
	public E indexOf(E e) {
		
			
				return e;
		
	}
	
	public void clear() {
		ptr = 0;
	}
	
	public int capacity() {
		return max;
	}
	
	public int size() {
		return ptr;
	}
	
	public boolean isEmpty() {
		return ptr <=0;
	}
	
	public boolean isFull() {
		return ptr>=max;
	}
	public void dump() {
		if(ptr<=0) {
			System.out.println("스택이 비어 있습니다");
		} else {
			for (int i = 0; i < ptr;i++) {
				System.out.println(stk[i] + " ");
			}
			System.out.println();
		}
	}

}
