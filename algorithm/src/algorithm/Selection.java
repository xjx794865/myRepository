package algorithm;

/**
 * 选择排序 int类型的数组
 * @author Lenovo
 *
 */
public class Selection {
    //私有化构造器 不允许外界调用
	private Selection() {

	}

	public static void selectionSort(int arr[]) {
		int n = arr.length;

		for (int i = 0; i < n; i++) {
			// 创建一个地址 存放最小索引
			int minIndex = i;

			// [i,n)的最小值
			for (int j = i + 1; j < n; j++) {

				if (arr[j] < arr[minIndex]) {

					minIndex = j;

					// 如果直接写 arr[i] = arr[minIndex] 只是交换地址 不交换值
					swap(arr, i, minIndex);

				}
			}
		}

	}

	public static void swap(int arr[], int i, int j) {

		int tep = arr[i];
		arr[i] = arr[j];
		arr[j] = tep;

	}

	public static void main(String[] args) {

		int a[] = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

		Selection.selectionSort(a);

		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}

	}

}
