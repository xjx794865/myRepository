package algorithm;

public class ShellSort {

	private ShellSort() {
	}

	public static void sort(Comparable[] arr) {

		int n = arr.length;

		// ���� increment sequence: 1, 4, 13, 40, 121, 364, 1093...
		// increment sequence������ �������⵫��������Ч����õ�
		int h = 1;
		// ������鳤��С��6����hΪ1 ������鳤�ȴ���5 С��15��hΪ4
		while (h < n / 3) {
			h = 3 * h + 1;
		}

		while (h >= 1) {

			// h-sort the array
			for (int i = h; i < n; i++) {

				// �� arr[i], arr[i-h], arr[i-2*h], arr[i-3*h]... ʹ�ò�������
				Comparable e = arr[i];
				int j = i;
				for (; j >= h && e.compareTo(arr[j - h]) < 0; j -= h) {
					arr[j] = arr[j - h];

				}
				arr[j] = e;
			}

			h /= 3;
		}
	}

	public static void main(String[] args) {

		Integer[] a = { 9, 7, 6, 5, 4, 1 };

		ShellSort.sort(a);

		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + " ");

	}

}
