package algorithm;

public class BubbleSort {

	private BubbleSort() {
	}

	public static void sort(Comparable[] arr) {

		int n = arr.length;

		boolean swapped = false;

		do {
			swapped = false;

			for (int i = 1; i < n; i++) {
				if (arr[i - 1].compareTo(arr[i]) > 0) {
					swap(arr, i - 1, i);

					swapped = true;
				}

			}
			// �Ż�, ÿһ��Bubble Sort��������Ԫ�ط���������λ��
			// ������һ������, ����Ԫ�ؿ��Բ��ٿ���
			n--;
		} while (swapped);

	}

	private static void swap(Object[] arr, int i, int j) {

		Object tem = arr[i];

		arr[i] = arr[j];

		arr[j] = tem;

	}

	public static void main(String[] args) {

		Integer[] a = { 8, 9, 5, 4, 7, 6, 2, 1, 0 };

		BubbleSort.sort(a);

		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");

	}

}
