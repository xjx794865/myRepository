package algorithm;

//�ڶѵ��йز����У���Ҫ�Ƚ϶���Ԫ�صĴ�С������Item��Ҫextends Comparable
public class MaxHeap<Item extends Comparable> {

 protected Item[] data;
 protected int count;
 protected int capacity;

 // ���캯��, ����һ���ն�, ������capacity��Ԫ��
 public MaxHeap(int capacity){
     data = (Item[])new Comparable[capacity+1];
     count = 0;
     this.capacity = capacity;
 }

 // ���캯��, ͨ��һ���������鴴��һ������
 // �ù���ѵĹ���, ʱ�临�Ӷ�ΪO(n)
 public MaxHeap(Item arr[]){

     int n = arr.length;

     data = (Item[])new Comparable[n+1];
     capacity = n;

     for( int i = 0 ; i < n ; i ++ )
         data[i+1] = arr[i];
     count = n;

     for( int i = count/2 ; i >= 1 ; i -- )
         shiftDown(i);
 }

 // ���ض��е�Ԫ�ظ���
 public int size(){
     return count;
 }

 // ����һ������ֵ, ��ʾ�����Ƿ�Ϊ��
 public boolean isEmpty(){
     return count == 0;
 }

 // �������в���һ���µ�Ԫ�� item
 public void insert(Item item){

     assert count + 1 <= capacity;
     data[count+1] = item;
     count ++;
     shiftUp(count);
 }

 // ��������ȡ���Ѷ�Ԫ��, ���������洢���������
 public Item extractMax(){
     assert count > 0;
     Item ret = data[1];

     swap( 1 , count );
     count --;
     shiftDown(1);

     return ret;
 }

 // ��ȡ�����еĶѶ�Ԫ��
 public Item getMax(){
     assert( count > 0 );
     return data[1];
 }


 // ������������Ϊi��j������Ԫ��
 private void swap(int i, int j){
     Item t = data[i];
     data[i] = data[j];
     data[j] = t;
 }

 //********************
 //* ���Ѻ��ĸ�������
 //********************
 private void shiftUp(int k){

     while( k > 1 && data[k/2].compareTo(data[k]) < 0 ){
         swap(k, k/2);
         k /= 2;
     }
 }

 private void shiftDown(int k){

     while( 2*k <= count ){
         int j = 2*k; // �ڴ���ѭ����,data[k]��data[j]����λ��
         if( j+1 <= count && data[j+1].compareTo(data[j]) > 0 )
             j ++;
         // data[j] �� data[2*k]��data[2*k+1]�е����ֵ

         if( data[k].compareTo(data[j]) >= 0 ) break;
         swap(k, j);
         k = j;
     }
 }

 // ���� MaxHeap
 public static void main(String[] args) {

     MaxHeap<Integer> maxHeap = new MaxHeap<Integer>(100);
     int N = 100; // ����Ԫ�ظ���
     int M = 100; // ����Ԫ��ȡֵ��Χ[0, M)
     for( int i = 0 ; i < N ; i ++ )
         maxHeap.insert( new Integer((int)(Math.random() * M)) );

     Integer[] arr = new Integer[N];
     // ��maxheap�е�������ʹ��extractMaxȡ����
     // ȡ������˳��Ӧ���ǰ��մӴ�С��˳��ȡ������
     for( int i = 0 ; i < N ; i ++ ){
         arr[i] = maxHeap.extractMax();
         System.out.print(arr[i] + " ");
     }
     System.out.println();

     // ȷ��arr�����ǴӴ�С���е�
     for( int i = 1 ; i < N ; i ++ )
         assert arr[i-1] >= arr[i];
 }
}