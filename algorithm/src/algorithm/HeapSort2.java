package algorithm;

import com.itcast.entity.SortTestHelper;

public class HeapSort2 {

    // ���ǵ��㷨�಻��������κ�ʵ��
    private HeapSort2(){}

    // ������arr����ʹ��HeapSort2����
    // HeapSort2, �������ǵ�heapify���̴�����
    // ��ʱ, �����ѵĹ���ʱ�临�Ӷ�ΪO(n), ������Ԫ�����δӶ���ȡ����, ʵ�����Ӷ�ΪO(nlogn)
    // �����������ʱ�临�Ӷ���Ȼ��O(nlogn), ���Ǳ�HeapSort1���ܸ���, ��Ϊ�����ѵ����ܸ���
    public static void sort(Comparable[] arr){

        int n = arr.length;
        MaxHeap<Comparable> maxHeap = new MaxHeap<Comparable>(arr);
        for( int i = n-1 ; i >= 0 ; i -- )
            arr[i] = maxHeap.extractMax();
    }

    // ���� HeapSort2
    public static void main(String[] args) {

        int N = 1000000;
        Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
        SortTestHelper.testSort("algorithm.HeapSort2", arr);

        return;
    }
}