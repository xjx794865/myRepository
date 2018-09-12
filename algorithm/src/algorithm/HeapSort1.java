package algorithm;

import com.itcast.entity.SortTestHelper;

public class HeapSort1 {

    // ���ǵ��㷨�಻��������κ�ʵ��
    private HeapSort1(){}

    // ������arr����ʹ��HeapSort1����
    // HeapSort1, �����е�Ԫ��������ӵ�����, �ڽ�����Ԫ�شӶ�������ȡ����, �����������
    // �����Ǵ����ѵĹ���, ���ǴӶ�������ȡ��Ԫ�صĹ���, ʱ�临�ӶȾ�ΪO(nlogn)
    // ���������������ʱ�临�Ӷ�ΪO(nlogn)
    public static void sort(Comparable[] arr){

        int n = arr.length;
        MaxHeap<Comparable> maxHeap = new MaxHeap<Comparable>(n);
        for( int i = 0 ; i < n ; i ++ )
            maxHeap.insert(arr[i]);

        for( int i = n-1 ; i >= 0 ; i -- )
            arr[i] = maxHeap.extractMax();
    }

    // ���� HeapSort1
    public static void main(String[] args) {

        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
        SortTestHelper.testSort("algorithm.HeapSort1", arr);

        return;
    }
}