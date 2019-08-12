package chap04;

public class Q1 {
	private int max;
	private int ptr;
	private int[] stk;
	
	public class EmptyIntStackException extends RuntimeException{
		public EmptyIntStackException(){
			
		}
	}
	
	public class OverflowIntStackException extends RuntimeException{
		public OverflowIntStackException() {
			
		}
	}
	
	public Q1(int capacity) {
		ptr = 0;
		max = capacity;
		try {
			stk = new int[max];
		} catch (OutOfMemoryError e) {
			max = 0;
		}
	}
	
	public int push(int inputNum) throws OverflowIntStackException{
		if(ptr>=max) {
			throw new OverflowIntStackException();
		}
		stk[ptr++] = inputNum;
		return inputNum;
	}
	
	public int pop() throws EmptyIntStackException{
		if(ptr<=0) {
			throw new EmptyIntStackException();
		}
		int temp;
		temp = stk[ptr-1];
		stk[ptr-1] = 0;
		ptr--;
		return temp;
	}
	public int peek() throws EmptyIntStackException{
		if(ptr<=0)
			throw new EmptyIntStackException();
		return stk[ptr -1];
	}
	
	public int indexOf(int searchNum) {
		for(int i = ptr -1;i >=0; i--) {
			if(stk[i]== searchNum) {
				return i;
				
			}			
		}
		return -1;		
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
