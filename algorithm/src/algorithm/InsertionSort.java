package algorithm;

import com.itcast.entity.SortTestHelper;
/**
 * 插入排序的第二种方法
 * @author Lenovo
 *
 */
public class InsertionSort {

	private InsertionSort() {
	}

	public static void sort(Comparable[] arr) {

		int n = arr.length;

		for (int i = 1; i < n; i++) {
            //将要比较的数存起来
			Comparable e = arr[i];
   
			int j;//j保存着e最终存放的位置
			for (j = i; j > 0 && arr[j - 1].compareTo(e) > 0; j--) {

				arr[j] = arr[j - 1];

			}
	        
			arr[j] = e;
			

		}
	}


	// 测试InsertionSort
	public static void main(String[] args) {

		
		Integer[] arr = {8,7,6,5};
		InsertionSort.sort(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println("");
		SortTestHelper.testSort("algorithm.InsertionSort", arr);
	}

}
