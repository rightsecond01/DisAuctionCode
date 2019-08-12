package chap03;

public class Q5 {

	static int binSearchX(int[] a, int n, int key) {
		int pl = 0;
		int pr = n - 1;

		do {
			int pc = (pl + pr) / 2;
			for (int i = 0; i < pc; i++) {
				if (a[i] == a[pc]) {
					pc = i;
					break;
				}
			}
			if (a[pc] == key) {
				return pc;
			} else if (a[pc] < key) {
				pl = pc + 1;
			} else {
				pr = pc - 1;
			}
		} while (pl <= pr);

		return -1;
	}

	public static void main(String[] args) {
		int[] a = {1,3,5,7,7,7,7,8,8,9,9};
		int n = a.length;
		int key = 8;
		int answer = Q5.binSearchX(a, n, key);
		System.out.println(answer);

	}

}
