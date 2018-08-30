package algorithm;

public class BubbleSort2 {

	private BubbleSort2() {
	}

	public static void sort(Comparable[] arr) {

		int n = arr.length;

		int newn;

		do {
			newn = 0;

			for (int i = 1; i < n; i++) {
				if (arr[i - 1].compareTo(arr[i]) > 0) {
					swap(arr, i - 1, i);

					// ��¼���һ�εĽ���λ��,�ڴ�֮���Ԫ������һ��ɨ���о�������
					newn = i;
				}
			}

			n = newn;

		} while (newn > 0);

	}

	private static void swap(Object[] arr, int i, int j) {
		Object tem = arr[i];

		arr[i] = arr[j];

		arr[j] = tem;

	}

	public static void main(String[] args) {

		Integer[] a = { 8, 9, 5, 4, 3, 7, 6, 2, 1, 0 };

		BubbleSort2.sort(a);

		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");

	}

}
