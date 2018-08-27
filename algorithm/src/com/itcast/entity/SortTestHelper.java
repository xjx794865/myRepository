package com.itcast.entity;

import java.lang.reflect.Method;

/**
 * 自动生成rangL~rangeR范围的随机数组 打印输入数组 判断数组是否有序 所用排序算法排序所用的时间
 * 
 * @author Lenovo
 *
 */
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
			arr[i] = new Integer((int) (Math.random() * (rangeR - rangeL + 1) + rangeL));

		}

		return arr;
	}

	// 打印输出数组
	public static void printArray(Object arr[]) {

		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println("");
	}

	// 判断arr数组是否有序
	public static boolean isSorted(Comparable[] arr) {
		for (int i = 0; i < arr.length - 1; i++)

			if (arr[i].compareTo(arr[i + 1]) > 0)

				return false;

		return true;

	}

	// 测试sortClassName所对应的排序算法排序arr数组所得到结果的正确性和算法运行时间
	public static void testSort(String sortClassName, Comparable[] arr) {

		// 通过java的反射机制，通过排序的类名，运行排序函数
		// 使用反射机制 现在还是看不懂 待研究
		try {

			// 通过sortClassName获得排序函数的class对象
			Class sortClass = Class.forName(sortClassName);
			// 通过排序函数的Class对象获得排序方法
			Method sortMethod = sortClass.getMethod("sort", new Class[] { Comparable[].class });
			// 排序参数只有一个，是可比较数组arr
			Object[] params = new Object[] { arr };

			long startTime = System.currentTimeMillis();
			// 调用排序函数
			sortMethod.invoke(null, params);

			long endTime = System.currentTimeMillis();

			assert isSorted(arr);

			System.out.println(sortClass.getSimpleName() + " : " + (endTime - startTime) + " ms ");

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}

	// 生成一个近乎有序的数组
	// 首先生成一个含有[0...n-1]的完全有序数组, 之后随机交换swapTimes对数据
	// swapTimes定义了数组的无序程度:
	// swapTimes == 0 时, 数组完全有序
	// swapTimes 越大, 数组越趋向于无序
	public static Integer[] generateNearlyOrderedArray(int n, int swapTimes) {
		Integer[] arr = new Integer[n];

		for (int i = 0; i < n; i++) {
			arr[i] = new Integer(i);
		}

		for (int i = 0; i < swapTimes; i++) {
			int a = (int) (Math.random() * n);
			int b = (int) (Math.random() * n);
			int t = arr[a];
			arr[a] = arr[b];
			arr[b] = t;
		}
		
		return arr;
	}

}
