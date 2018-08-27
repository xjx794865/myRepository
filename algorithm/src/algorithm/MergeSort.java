package algorithm;

import java.util.Arrays;

import com.itcast.entity.SortTestHelper;

public class MergeSort {
	
	  // ���ǵ��㷨�಻��������κ�ʵ��
    private MergeSort(){}

    // ��arr[l...mid]��arr[mid+1...r]�����ֽ��й鲢
    private static void merge(Comparable[] arr, int l, int mid, int r) {

        Comparable[] aux = Arrays.copyOfRange(arr, l, r+1);

        // ��ʼ����iָ����벿�ֵ���ʼ����λ��l��jָ���Ұ벿����ʼ����λ��mid+1
        int i = l, j = mid+1;
        for( int k = l ; k <= r; k ++ ){

            if( i > mid ){  // �����벿��Ԫ���Ѿ�ȫ���������
                arr[k] = aux[j-l]; j ++;
            }
            else if( j > r ){   // ����Ұ벿��Ԫ���Ѿ�ȫ���������
                arr[k] = aux[i-l]; i ++;
            }
            else if( aux[i-l].compareTo(aux[j-l]) < 0 ){  // ��벿����ָԪ�� < �Ұ벿����ָԪ��
                arr[k] = aux[i-l]; i ++;
            }
            else{  // ��벿����ָԪ�� >= �Ұ벿����ָԪ��
                arr[k] = aux[j-l]; j ++;
            }
        }
    }

    // �ݹ�ʹ�ù鲢����,��arr[l...r]�ķ�Χ��������
    private static void sort(Comparable[] arr, int l, int r) {

        if (l >= r)
            return;

        int mid = (l+r)/2;
        sort(arr, l, mid);
        sort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    public static void sort(Comparable[] arr){

        int n = arr.length;
        sort(arr, 0, n-1);
    }

    // ����MergeSort
    public static void main(String[] args) {

        // Merge Sort������ѧϰ�ĵ�һ��O(nlogn)���Ӷȵ��㷨
        // ������1��֮�����ɴ���100��������������
        // ע�⣺��Ҫ���׳���ʹ��SelectionSort, InsertionSort����BubbleSort����100�򼶵�����
        // ������ͼ�ʶ��O(n^2)���㷨��O(nlogn)�㷨�ı��ʲ��죺��
        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
        SortTestHelper.testSort("algorithm.MergeSort", arr);

        return;
    }

}
