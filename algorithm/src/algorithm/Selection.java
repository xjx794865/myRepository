package algorithm;

/**
 * ѡ������ int���͵�����
 * @author Lenovo
 *
 */
public class Selection {
    //˽�л������� ������������
	private Selection() {

	}

	public static void selectionSort(int arr[]) {
		int n = arr.length;

		for (int i = 0; i < n; i++) {
			// ����һ����ַ �����С����
			int minIndex = i;

			// [i,n)����Сֵ
			for (int j = i + 1; j < n; j++) {

				if (arr[j] < arr[minIndex]) {

					minIndex = j;

					// ���ֱ��д arr[i] = arr[minIndex] ֻ�ǽ�����ַ ������ֵ
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
