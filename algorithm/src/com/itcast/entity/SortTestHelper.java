package com.itcast.entity;

import java.lang.reflect.Method;

/**
 * �Զ�����rangL~rangeR��Χ��������� ��ӡ�������� �ж������Ƿ����� ���������㷨�������õ�ʱ��
 * 
 * @author Lenovo
 *
 */
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
			arr[i] = new Integer((int) (Math.random() * (rangeR - rangeL + 1) + rangeL));

		}

		return arr;
	}

	// ��ӡ�������
	public static void printArray(Object arr[]) {

		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println("");
	}

	// �ж�arr�����Ƿ�����
	public static boolean isSorted(Comparable[] arr) {
		for (int i = 0; i < arr.length - 1; i++)

			if (arr[i].compareTo(arr[i + 1]) > 0)

				return false;

		return true;

	}

	// ����sortClassName����Ӧ�������㷨����arr�������õ��������ȷ�Ժ��㷨����ʱ��
	public static void testSort(String sortClassName, Comparable[] arr) {

		// ͨ��java�ķ�����ƣ�ͨ�����������������������
		// ʹ�÷������ ���ڻ��ǿ����� ���о�
		try {

			// ͨ��sortClassName�����������class����
			Class sortClass = Class.forName(sortClassName);
			// ͨ����������Class���������򷽷�
			Method sortMethod = sortClass.getMethod("sort", new Class[] { Comparable[].class });
			// �������ֻ��һ�����ǿɱȽ�����arr
			Object[] params = new Object[] { arr };

			long startTime = System.currentTimeMillis();
			// ����������
			sortMethod.invoke(null, params);

			long endTime = System.currentTimeMillis();

			assert isSorted(arr);

			System.out.println(sortClass.getSimpleName() + " : " + (endTime - startTime) + " ms ");

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}

	// ����һ���������������
	// ��������һ������[0...n-1]����ȫ��������, ֮���������swapTimes������
	// swapTimes���������������̶�:
	// swapTimes == 0 ʱ, ������ȫ����
	// swapTimes Խ��, ����Խ����������
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
