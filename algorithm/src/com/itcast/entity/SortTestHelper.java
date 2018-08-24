package com.itcast.entity;

public class SortTestHelper {

	// 不允许产生任何实例
	private SortTestHelper() {
	}

	public static Integer[] generateRandomArray(int n, int rangeL, int rangeR) {

		// 断言检查 java在执行时默认是不启动断言检查的
		// assert rangeL <= rangeR;

		Integer[] arr = new Integer[n];

		for (int i = 0; i < n; i++) {

			// Math.random 返回的是0~1之间的随机数(可以取到0，无限接近1)
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
