package chap03;

public class Q3 {

	static int searchIdx(int[] a, int n, int key, int[] idx) {
		int i = 0;
		int j = 0;
		int count = 0;
		int result = 0;

		for (i = 0; i < n; i++) {
			if (a[i] == key) {
				count++;
			}
		}
		if (count == 0) {
			result = 0;
			return result;
		} else {
			idx = new int[count];
			for (i = 0; i < n; i++) {
				if (a[i] == key) {
					idx[j] = i;
					j++;
				}
			}
			return idx.length;
		}

	}

	public static void main(String[] args) {
		int[] a = { 1, 9, 2, 9, 4, 6, 7, 9 };
		int[] i = null;
		int length = searchIdx(a, 8, 9, i);
		System.out.println(length);

	}

}
