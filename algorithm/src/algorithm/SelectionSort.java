package algorithm;

import com.itcast.entity.SortTestHelper;
import com.itcast.entity.Student;


/**
 * 任意类型的数据 选择排序
 * @author Lenovo
 *
 */
public class SelectionSort {

	private SelectionSort() {
	}

	public static void sort(Comparable[] arr) {

		int n = arr.length;

		for (int i = 0; i < n; i++) {

			int minIndex = i;

			for (int j = i + 1; j < n; j++) {
				if (arr[j].compareTo(arr[minIndex]) < 0)

					minIndex = j;

				swap(arr,i, minIndex);

			}
		}

	}

	public static void swap(Object[] a, int i, int j) {
		Object tem = a[i];
		a[i] = a[j];
		a[j] = tem;
	}

	public static void main(String[] args) {
        //测试整型
		Integer[] a = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

		SelectionSort.sort(a);

		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println(" ");

		String[] b = { "C", "D", "T", "B", "A" };
		SelectionSort.sort(b);
		for (int i = 0; i < b.length; i++) {
			System.out.print(b[i] + " ");
		}
		System.out.println(" ");

		// 测试Double
		Double[] c = { 4.4, 3.3, 2.2, 1.1 };
	
		SelectionSort.sort(c);
		for (int i = 0; i < c.length; i++) {
			System.out.print(c[i] + " ");

		}
		System.out.println(" ");

		// 测试自定义的类 Student
		Student[] d = new Student[4];
		d[0] = new Student("D", 90);
		d[1] = new Student("C", 100);
		d[2] = new Student("B", 95);
		d[3] = new Student("A", 95);
		SelectionSort.sort(d);
		for (int i = 0; i < d.length; i++) {
			System.out.println(d[i]);
		}
		System.out.println("");
		
		int N = 10000;
		Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 500);
		SortTestHelper.testSort("algorithm.SelectionSort", arr);
		
		return;

	}

}
