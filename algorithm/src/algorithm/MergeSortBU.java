package algorithm;

import java.util.Arrays;


public class MergeSortBU {

	// ���ǵ��㷨�಻��������κ�ʵ��
	private MergeSortBU() {
	}

	// ��arr[l...mid]��arr[mid+1...r]�����ֽ��й鲢
	private static void merge(Comparable[] arr, int l, int mid, int r) {

		Comparable[] aux = Arrays.copyOfRange(arr, l, r + 1);

		// ��ʼ����iָ����벿�ֵ���ʼ����λ��l��jָ���Ұ벿����ʼ����λ��mid+1
		int i = l, j = mid + 1;
		for (int k = l; k <= r; k++) {

			if (i > mid) { // �����벿��Ԫ���Ѿ�ȫ���������
				arr[k] = aux[j - l];
				j++;
			} else if (j > r) { // ����Ұ벿��Ԫ���Ѿ�ȫ���������
				arr[k] = aux[i - l];
				i++;
			} else if (aux[i - l].compareTo(aux[j - l]) < 0) { // ��벿����ָԪ�� < �Ұ벿����ָԪ��
				arr[k] = aux[i - l];
				i++;
			} else { // ��벿����ָԪ�� >= �Ұ벿����ָԪ��
				arr[k] = aux[j - l];
				j++;
			}
		}
	}

	public static void sort(Comparable[] arr) {

		int n = arr.length;

		// Merge Sort Bottom Up ���Ż��汾
		for (int sz = 1; sz < n; sz *= 2)
			for (int i = 0; i < n - sz; i += sz + sz)
				// �� arr[i...i+sz-1] �� arr[i+sz...i+2*sz-1] ���й鲢
				merge(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1));

	}

	// ���� MergeSort BU
	public static void main(String[] args) {

		Integer[] a = { 9, 8, 7, 3};

		MergeSortBU.sort(a);

		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}

	}
}