package algorithm;

import com.itcast.entity.SortTestHelper;

public class QuickSort2 {
	// ���ǵ��㷨�಻��������κ�ʵ��
	private QuickSort2() {
	}

	// ˫·���������partition
	// ����p, ʹ��arr[l...p-1] < arr[p] ; arr[p+1...r] > arr[p]
	private static int partition(Comparable[] arr, int l, int r) {

		// �����arr[l...r]�ķ�Χ��, ѡ��һ����ֵ��Ϊ�궨��pivot
		swap(arr, l, (int) (Math.random() * (r - l + 1)) + l);

		Comparable v = arr[l];

		// arr[l+1...i) <= v; arr(j...r] >= v
		int i = l + 1, j = r;
		while (true) {
			// ע������ı߽�, arr[i].compareTo(v) < 0, ������arr[i].compareTo(v) <= 0
			// ˼��һ��Ϊʲô?
			while (i <= r && arr[i].compareTo(v) < 0)
				i++;

			// ע������ı߽�, arr[j].compareTo(v) > 0, ������arr[j].compareTo(v) >= 0
			// ˼��һ��Ϊʲô?
			while (j >= l + 1 && arr[j].compareTo(v) > 0)
				j--;

			// ��������������߽���趨, �е�ͬѧ�ڿγ̵��ʴ����кܺõĻش�:)
			// ��ҿ��Բο�: http://coding.imooc.com/learn/questiondetail/4920.html

			if (i > j)
				break;

			swap(arr, i, j);
			i++;
			j--;
		}

		swap(arr, l, j);

		return j;
	}

	// �ݹ�ʹ�ÿ�������,��arr[l...r]�ķ�Χ��������
	private static void sort(Comparable[] arr, int l, int r) {

		if (l >= r)
			return;

		int p = partition(arr, l, r);
		sort(arr, l, p - 1);
		sort(arr, p + 1, r);
	}

	public static void sort(Comparable[] arr) {

		int n = arr.length;
		sort(arr, 0, n - 1);
	}

	private static void swap(Object[] arr, int i, int j) {
		Object t = arr[i];
		arr[i] = arr[j];
		arr[j] = t;
	}

	// ���� QuickSort
	public static void main(String[] args) {

		// ˫·���������㷨Ҳ��һ��O(nlogn)���Ӷȵ��㷨
		// ������1��֮�����ɴ���100��������������
		int N = 1000000;
		Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
		SortTestHelper.testSort("algorithm.QuickSort2", arr);

		return;
	}
}
