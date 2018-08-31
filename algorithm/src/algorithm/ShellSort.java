package algorithm;

public class ShellSort {

	private ShellSort() {
	}

	public static void sort(Comparable[] arr) {

		int n = arr.length;

		// 计算 increment sequence: 1, 4, 13, 40, 121, 364, 1093...
		// increment sequence的增量 可以随意但是这种是效率最好的
		int h = 1;
		// 如果数组长度小于6，则h为1 如果数组长度大于5 小于15则h为4
		while (h < n / 3) {
			h = 3 * h + 1;
		}

		while (h >= 1) {

			// h-sort the array
			for (int i = h; i < n; i++) {

				// 对 arr[i], arr[i-h], arr[i-2*h], arr[i-3*h]... 使用插入排序
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
