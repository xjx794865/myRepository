package algorithm;

import com.itcast.entity.SortTestHelper;

public class QuickSort {

    // ���ǵ��㷨�಻��������κ�ʵ��
    private QuickSort(){}

    // ��arr[l...r]���ֽ���partition����
    // ����p, ʹ��arr[l...p-1] < arr[p] ; arr[p+1...r] > arr[p]
    private static int partition(Comparable[] arr, int l, int r){
    	
    	//ʹ�������������V��ʹ�ÿ��������㷨�ڽ��������������Ҳ����ʹ��
        swap(arr,l,(int)Math.random()*(l-r+1)+l);

        Comparable v = arr[l];

        int j = l; // arr[l+1...j] < v ; arr[j+1...i) > v
        for( int i = l + 1 ; i <= r ; i ++ )
            if( arr[i].compareTo(v) < 0 ){
                j ++;
                swap(arr, j, i);
            }

        swap(arr, l, j);

        return j;
    }

    // �ݹ�ʹ�ÿ�������,��arr[l...r]�ķ�Χ��������
    private static void sort(Comparable[] arr, int l, int r){

         if( l >= r )
            return;

        int p = partition(arr, l, r);
        sort(arr, l, p-1 );
        sort(arr, p+1, r);
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

    // ���� QuickSort
    public static void main(String[] args) {

        // Quick SortҲ��һ��O(nlogn)���Ӷȵ��㷨
        // ������1��֮�����ɴ���100��������������
    	int swapTimes = 100;
    	Integer[] arr = SortTestHelper.generateNearlyOrderedArray(1000000, swapTimes);
    	SortTestHelper.testSort("algorithm.QuickSort", arr);
    	
    	// int N = 1000000;
     //    Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
       //  SortTestHelper.testSort("algorithm.QuickSort", arr);
      
       }
    }

