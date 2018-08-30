package algorithm;

import com.itcast.entity.SortTestHelper;
/**
 * 插入排序的正常方法
 * @author Lenovo
 *
 */
public class InsertionSort2 {

	private InsertionSort2() {
	}

	public static void sort(Comparable[] arr) {
		
		int n = arr.length;

		for (int i = 1; i < n; i++) {

			for (int j = i; j > 0 && arr[j].compareTo(arr[j - 1]) < 0; j--) {
				swap(arr, j, j - 1);

			}

		}

	}

	private static void swap(Object[] arr, int i, int j) {

		Object tem = arr[i];
		arr[i] = arr[j];
		arr[j] = tem;

	}
    
	public static void main(String[] args) {
		Integer[] a = {8,5,6,7,2,1};
		InsertionSort2.sort(a);
		SortTestHelper.printArray(a);
		
	}

}
