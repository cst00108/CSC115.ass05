import java.util.NoSuchElementException;

/*
 * The shell of the class, to be completed as part
 * of CSC115 Assignment 5: Emergency Room.
 */

@SuppressWarnings({"unchecked"})

/*
 * Complete this class as per the Heap.html specification document.
 * Fill in any of the parts that have the comment:
 *	/********  COMPLETE *******
 * Do not change method headers or code that has been supplied.
 * Delete all messages to you, including this one, before submitting.
 */

public class Heap {
	private Comparable[] heapArray;
    private static final int SIZE = 1000;
	private int size = 0;
        
	public Heap() {
            heapArray = new Comparable[SIZE];
	}
        
	private void bubbleUp(){

	}

	private void bubbleDown(){

	}

	public void insert(Comparable item){
		try{
			heapArray[size] = item;
			size++;
		} catch(ArrayIndexOutOfBoundsException x){
			increaseSize();
			insert(item);
		}
		
		bubbleUp();
	}
	
	public boolean isEmpty(){
		return size == 0;
	}

	public int size(){
		return size;
	}
	
	public Comparable getRootItem(){
		return heapArray[0];
	}
	
	private void increaseSize(){
		Comparable[] newHeapArray = new Comparable[heapArray.length * 2];

		for(int index=0; index<size; index++){
			newHeapArray[index] = heapArray[index];
		}
		
		heapArray = newHeapArray;
	}

	public Comparable removeRootItem(){
		Comparable toReturn = heapArray[0];
//double check if decreases size AFTER the assignment
		heapArray[0] = heapArray[size--];
		
		bubbleDown();
		
		return toReturn;
	}
	
	public static void main(String[] aaarg){
		int[] ints = new int[0];
		
		ints[0] = -1;
	}
	
}
