import java.util.*;

public class PQ225 {

	private static final int CAPACITY = 15000;
	private int size;
	private int[] heap;
	
	//Blank constructor for the heap array
	public PQ225() {
		
		size = 0;
		
		heap = new int[CAPACITY];
	}
	
	/*
	 * Method: less
	 * 
	 * Will compare two elements and return true if i < j
	 */
	private boolean less(int i, int j) {
		
		if (heap[i] - heap[j] < 0)
			return true;
		return false;
		
	}
	
	/*
	 * Method: swap
	 * 
	 * Will swap two elements in the array
	 */
	private void swap(int i, int j) {
		
		int temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	/*
	 * Method: swim
	 * 
	 * This section of code has been used from page 316 of the textbook
	 */
	private void swim(int k)
	{
		while (k > 1 && less(k/2, k)) {
			swap(k/2, k);
			k = k/2;
		}
	}
	
	/*
	 * Method: ranGen
	 */
	public void ranGen(int N, int LOW, int HIGH) {
		
		Random rand = new Random(System.currentTimeMillis());
		int index = 1;
		
		while (index < N + 1) {
			heap[index] = rand.nextInt(((HIGH - LOW) + 1) + LOW);
			System.out.print(heap[index] + " ");
			index++;
			size++;
		}
		
		System.out.println();
		
	}
	
	/*
	 * This method will return the size of the heap.
	 */
	public int size() {
		return size;
	}
	
	/*
	 * makeHeap
	 * 
	 * This method will turn the unsorted integer array into a heap
	 * 
	 * 
	 */
	public void makeHeap(int[] heap) {
		
		int tempSize = size;
		while (tempSize >= 1)
		{
			swim(heap[tempSize]);
			tempSize /= 2;
		}
	}
	/**
	 * Print heap
	 *
	 */
	public void printHeap(){
		for (int i = 1; i < size + 1; i++)
			System.out.print(heap[i] + " ");
	}
	
	/*
	 * test
	 */
	
	public void test() {
		makeHeap(heap);
	}
	
	/*
	 * This is the main method that will be used for testing.
	 * 
	 * 
	 * Currently in progress: Whether we want to put a user input on bounds
	 */
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		PQ225 test = new PQ225();
		
		System.out.println("Please the amount of integers you'd like to randomly create: ");
		int initialHeapSize = input.nextInt();
		
		
		//Test for random number generator passes
		test.ranGen(initialHeapSize, 0, 100);
		
		
		test.test();
		test.printHeap();
		
		
		input.close();
	}
}
