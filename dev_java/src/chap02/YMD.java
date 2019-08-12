package chap02;

import java.util.Scanner;

public class YMD {// Q11, P93
	int y;
	int m;
	int d;

	public YMD(int y, int m, int d) {
		this.y = y;
		this.m = m;
		this.d = d;
	}

	static int[][] mdays = { { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
			                 {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }, };
	
	

	static int isLeap(int year) {
		return (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) ? 1 : 0;
	}

	public String after(int n) {
		int plusDay = d + n;
		int plusYear = y;
		int index = m;
		while (plusDay - mdays[isLeap(plusYear)][index - 1] > 0) {
			plusDay = plusDay - mdays[isLeap(plusYear)][index - 1];
			index++;
			if (index > 12) {
				index = 1;
				plusYear++;
			}
		}
		return plusYear + "년" + index + "월" + plusDay + "일";
	}

	public String before(int n) {
		int subDay = 0;
		int indexMonth = m;
		int subYear = y;

		if (d > n) {
			subDay = d - n;
		} else {
			while (d <= n) {
				if (indexMonth == 1) {
					indexMonth = 12;
					subYear--;
				} else {
					indexMonth--;
				}
				d = d + mdays[isLeap(subYear)][indexMonth - 1];
			}
			subDay = d - n;
		}

		return subYear + "년" + indexMonth + "월" + subDay + "일";
	}
	
	

	public static void main(String[] agrs) {
		int n;
		do {
			Scanner sc = new Scanner(System.in);
			System.out.println("n일전,n일후 날짜 계산기입니다.");
			System.out.println("기준날짜를 입력해 주세요");
			System.out.print("년 :");
			int inputYear = sc.nextInt();
			System.out.print("월 : ");
			int inputMonth = sc.nextInt();
			System.out.print("일 : ");
			int inputDay = sc.nextInt();
			String standardDay = inputYear + "년" + inputMonth + "월" + inputDay + "일";
			System.out.println("\n" + "기준일은 " + standardDay);

			YMD obj = new YMD(inputYear, inputMonth, inputDay);

			System.out.println("기준날짜보다 n일전 검색은 [이전], 기준날짜보다 n일후 검색은[이후]를 입력하세요 ");
			String callMethod = sc.next();

			if (callMethod.equals("이전")) {
				System.out.println("n일전 검색입니다. 기준일로부터 몇일전을 알고싶나요?");
				System.out.print("입력 : ");
				int bf = sc.nextInt();
				String beforeDay = obj.before(bf);
				System.out.println(standardDay + "로부터 " + bf + "일 이전 날짜는 " + beforeDay + "입니다.");
			} else if (callMethod.equals("이후")) {
				System.out.println("n일후 검색입니다. 기준일로부터 몇일뒤를 알고싶나요?");
				System.out.print("입력 : ");
				int af = sc.nextInt();
				String afterDay = obj.after(af);
				System.out.println(standardDay + "로부터 " + af + "일 이후 날짜는 " + afterDay + "입니다.");
			}
			
			System.out.println("처음부터 다시하시려면 0을, 끝내시려면 1을 입력하세요");
			n = sc.nextInt();
		} while (n != 1);

	}

}
