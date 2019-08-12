package chap03;

public class Q1 {

	static int seqSearchSen(int[] a, int n, int key) {
		int i = 0;
		for (i = 0; i < n; i++) {
			if (a[i] == key) {
				break;
			}
		}
		return i == n ? -1 : i;
	}

	public static void main(String[] args) {

		int[] num = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		int k = seqSearchSen(num, 10, 5);
		System.out.println("5는 " +"num"+ "[" + k + "]에 있습니다." );
	}

}
