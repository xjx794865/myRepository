package algorithm;

import com.itcast.entity.SortTestHelper;

public class QuickSort3 {
	

    // ���ǵ��㷨�಻��������κ�ʵ��
    private QuickSort3(){}

    // �ݹ�ʹ�ÿ�������,��arr[l...r]�ķ�Χ��������
    private static void sort(Comparable[] arr, int l, int r){

        // ����С��ģ����, ʹ�ò�������
        if( r - l <= 15 ){
            InsertionSort.sort(arr, l, r);
            return;
        }

        // �����arr[l...r]�ķ�Χ��, ѡ��һ����ֵ��Ϊ�궨��pivot
        swap( arr, l, (int)(Math.random()*(r-l+1)) + l );

        Comparable v = arr[l];

        int lt = l;     // arr[l+1...lt] < v
        int gt = r + 1; // arr[gt...r] > v
        int i = l+1;    // arr[lt+1...i) == v
        while( i < gt ){
            if( arr[i].compareTo(v) < 0 ){
                swap( arr, i, lt+1);
                i ++;
                lt ++;
            }
            else if( arr[i].compareTo(v) > 0 ){
                swap( arr, i, gt-1);
                gt --;
            }
            else{ // arr[i] == v
                i ++;
            }
        }

        swap( arr, l, lt );

        sort(arr, l, lt-1);
        sort(arr, gt, r);
    }

    public static void sort(Comparable[] arr){

        int n = arr.length;
        sort(arr, 0, n-1);
    }

    private static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
        
    }

    // ���� QuickSort3Ways
    public static void main(String[] args) {

        // ��·���������㷨Ҳ��һ��O(nlogn)���Ӷȵ��㷨
        // ������1��֮�����ɴ���100��������������
        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
        SortTestHelper.testSort("a"
        		+ ""
        		+ ""
        		+ "lgorithm.QuickSort3", arr);

        return;
    }

}
