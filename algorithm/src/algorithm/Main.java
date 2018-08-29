package algorithm;

import java.util.Arrays;

import com.itcast.entity.SortTestHelper;

/**
 * ���ڱȽ�ѡ������Ͳ������������Ч��
 * 
 * @author Lenovo
 *
 */
public class Main {

	public static void main(String[] args) {
		int N = 8000;
		System.out.println("Test for random array , size =" + N + ",random range[0 ," + N + "]");

		Integer[] arr1 = SortTestHelper.generateRandomArray(N, 0, N);
		Integer[] arr2 = Arrays.copyOf(arr1, arr1.length);
		
		SortTestHelper.testSort("algorithm.InsertionSort", arr2);
		SortTestHelper.testSort("algorithm.MergeSort", arr1);

	}

}
