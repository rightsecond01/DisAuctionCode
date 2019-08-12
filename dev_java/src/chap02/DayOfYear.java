package chap02;

public class DayOfYear {

	static int[][] mdays = { { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
			                 { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }, 
			                 };

	static int isLeap(int year) {
		return (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) ? 1 : 0;
	}

	static int dayOfYear(int y, int m, int d) {
		
		while(m-1>0) {
			d= d + mdays[isLeap(y)][m-2];
			m--;
		}
		
		return d; 
	}
	
	static int leftDayOfYear(int y, int m, int d) {
		
		while(m-1>0) {
			d= d + mdays[isLeap(y)][m-2];
			m--;
		}
		d = 365-d +isLeap(y);
		
		return d;
	}
	public static void main(String[] agrs) {
		int days = dayOfYear(2011,5,21);
		System.out.println(isLeap(2011));
		System.out.println(days);
	}

}
