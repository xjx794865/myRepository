package algorithm;

import com.itcast.entity.SortTestHelper;
/**
 * ��������ĵڶ��ַ���
 * @author Lenovo
 *
 */
public class InsertionSort {

	private InsertionSort() {
	}

	public static void sort(Comparable[] arr) {

		int n = arr.length;

		for (int i = 1; i < n; i++) {
            //��Ҫ�Ƚϵ���������
			Comparable e = arr[i];
   
			int j;//j������e���մ�ŵ�λ��
			for (j = i; j > 0 && arr[j - 1].compareTo(e) > 0; j--) {

				arr[j] = arr[j - 1];

			}
	        
			arr[j] = e;
			

		}
	}


	// ����InsertionSort
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
