package chap04;

import java.util.Scanner;

public class Q1_Test {

	public static void main(String[] args) {
		Q1 q = new Q1(64);
		Scanner stdIn = new Scanner(System.in);
		
		while(true) {
			System.out.println("현재 데이터 수 : " + q.size() + " / " + q.capacity());
			System.out.print("(1)푸시   (2)팝   (3)피크   (4)덤프   (5)검색   (6)비우기   (0)종료  : ");
			
			int menu = stdIn.nextInt();
			if(menu == 0) break;//종료
			
			int data;
			
			switch(menu) {
			case 1: //푸시
				System.out.print("데이터 : ");
				data = stdIn.nextInt();
				try {
					q.push(data);
				} catch (Q1.OverflowIntStackException e) {
					System.out.println("스택이 가득 찼습니다.");
					// TODO: handle exception
				}
				break;
			///////////////////////////////////////////////////	
			case 2	: //팝
				try {
					data = q.pop();
					System.out.println("팝한 데이터는 " + data + "입니다.");
				} catch (Q1.EmptyIntStackException e) {
					System.out.println("스택이 비어 있습니다.");
				}
				break;
			///////////////////////////////////////////////////
			case 3 : //스택 데이터중 가장 위의 데이터를 빼오기 
				try {
					data = q.peek();
					System.out.println("피크한 데이터는 " + data + "입니다.");
				} catch (Q1.EmptyIntStackException e) {
					System.out.println("스택이 비어 있습니다.");
				}
				break;
			///////////////////////////////////////////////////
			case 4 :
				q.dump();
				break;
			///////////////////////////////////////////////////
			case 5 : 
				System.out.print("검색할 데이터 : ");
				data = stdIn.nextInt();
				int index = q.indexOf(data);
				if(index == -1) {
					System.out.println("검색하신 데이터가 존재하지 않습니다");
				}else {
					System.out.println("검색하신 데이터는 " + (index+1) + "번째에 존재합니다");
				}
				break;
			///////////////////////////////////////////////////
			case 6 : 
				q.clear();
				break;
			}
			
		}

	}

}
