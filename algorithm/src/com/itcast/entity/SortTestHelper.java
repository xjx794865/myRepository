package com.itcast.entity;

public class SortTestHelper {

	// ����������κ�ʵ��
	private SortTestHelper() {
	}

	public static Integer[] generateRandomArray(int n, int rangeL, int rangeR) {

		// ���Լ�� java��ִ��ʱĬ���ǲ��������Լ���
		// assert rangeL <= rangeR;

		Integer[] arr = new Integer[n];

		for (int i = 0; i < n; i++) {

			// Math.random ���ص���0~1֮��������(����ȡ��0�����޽ӽ�1)
			arr[i] = new Integer((int) (Math.random() * (rangeR - rangeL +1) + rangeL));

		}

		return arr;
	}

	public static void printArray(Object arr[]) {

		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println("");
	}

}
