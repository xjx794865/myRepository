package algorithm;

import com.itcast.entity.SortTestHelper;

//��ʹ��һ�����������, ֱ����ԭ�����Ͻ���ԭ�صĶ�����
public class HeapSort {

 // ���ǵ��㷨�಻��������κ�ʵ��
 private HeapSort(){}

 public static void sort(Comparable[] arr){

     int n = arr.length;

     // ע�⣬��ʱ���ǵĶ��Ǵ�0��ʼ������
     // ��(���һ��Ԫ�ص�����-1)/2��ʼ
     // ���һ��Ԫ�ص����� = n-1
     for( int i = (n-1-1)/2 ; i >= 0 ; i -- )
         shiftDown2(arr, n, i);

     for( int i = n-1; i > 0 ; i-- ){
         swap( arr, 0, i);
         shiftDown2(arr, i, 0);
     }
 }

 // ������������Ϊi��j������Ԫ��
 private static void swap(Object[] arr, int i, int j){
     Object t = arr[i];
     arr[i] = arr[j];
     arr[j] = t;
 }

 // ԭʼ��shiftDown����
 private static void shiftDown(Comparable[] arr, int n, int k){

     while( 2*k+1 < n ){
         int j = 2*k+1;
         if( j+1 < n && arr[j+1].compareTo(arr[j]) > 0 )
             j += 1;

         if( arr[k].compareTo(arr[j]) >= 0 )break;

         swap( arr, k, j);
         k = j;
     }
 }

 // �Ż���shiftDown����, ʹ�ø�ֵ�ķ�ʽȡ�����ϵ�swap,
 // ���Ż�˼�������֮ǰ�Բ�����������Ż���˼·��һ�µ�
 private static void shiftDown2(Comparable[] arr, int n, int k){

     Comparable e = arr[k];
     while( 2*k+1 < n ){
         int j = 2*k+1;
         if( j+1 < n && arr[j+1].compareTo(arr[j]) > 0 )
             j += 1;

         if( e.compareTo(arr[j]) >= 0 )
             break;

         arr[k] = arr[j];
         k = j;
     }

     arr[k] = e;
 }

 // ���� HeapSort
 public static void main(String[] args) {

     int N = 1000000;
     Integer[] arr = SortTestHelper.generateRandomArray(N, 0, 100000);
     SortTestHelper.testSort("algorithm.HeapSort", arr);

     return;
 }
}