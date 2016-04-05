/*
 * Name:      Jason Donald
 * ID:        V00861539
 * Date:      2006-04-04
 * Filename:  Heap.java
 * Details:   CSC115 Assignment 5
 */

import java.util.NoSuchElementException;
import java.util.Random;

@SuppressWarnings({"unchecked"})

public class Heap {
	private Comparable[] heapArray;
    private static final int SIZE = 1000;
	private int size = 0;

	/**
	 * Create an empty heap.
	 */
	public Heap() {
            heapArray = new Comparable[SIZE];
	}
        
	private void bubbleUp(){
		for(int childIndex = size-1, parentIndex = childIndex/2; 
				childIndex != parentIndex 
				&& heapArray[childIndex].compareTo(heapArray[parentIndex]) < 0;
				childIndex = parentIndex, parentIndex /= 2){
			
			Comparable temp = heapArray[childIndex];
			heapArray[childIndex] = heapArray[parentIndex];
			heapArray[parentIndex] = temp;
		}
	}

	private void bubbleDown(){
		for(int parentIndex = 0, childToCompare = -1;
				true;
				parentIndex = childToCompare){
	
			int firstChildIndex = 2 * parentIndex + 1; 
			int secondChildIndex = firstChildIndex + 1;
			
			//if just one child, skip the first compareTo().
			//This means the first born is the last element in the array.
			if(firstChildIndex >= this.size()){ //reached the end
				break;
			} else if(secondChildIndex >= this.size()){
				childToCompare = firstChildIndex;
			} else { //parent has two chidren
				int result = 
						heapArray[firstChildIndex]
						.compareTo(heapArray[secondChildIndex]);
				
				childToCompare = 
						(result < 0) ? firstChildIndex : secondChildIndex; 
			}
			
			int result = 
					heapArray[childToCompare].compareTo(heapArray[parentIndex]);
			
			if(result < 0){ //swap the parent and the child
				Comparable temp = heapArray[childToCompare];

				heapArray[childToCompare] = heapArray[parentIndex];
				heapArray[parentIndex] = temp;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Inserts an item into the heap.
	 * @param item The newly added item.
	 */
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
	
	/**
	 * @return True if the heap is empty, false if it is not.
	 */
	public boolean isEmpty(){
		return size == 0;
	}

	/**
	 * @return The number of items in the heap.
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Retrieves, without removing the item in the root.
	 * @return The top item in the tree.
	 * @throws java.util.NoSuchElementException if the heap is empty.
	 */
	public Comparable getRootItem(){
		throwIfEmpty(); //throws exception if no elements

		return heapArray[0];
	}
	
	private void increaseSize(){
		Comparable[] newHeapArray = new Comparable[heapArray.length * 2];

		for(int index=0; index<size; index++){
			newHeapArray[index] = heapArray[index];
		}
		
		heapArray = newHeapArray;
	}

	/**
	 * Removes the item at the root node of the heap.
	 * @return The item at the root of the heap.
	 * @throws java.util.NoSuchElementException if the heap is empty.
	 */
	public Comparable removeRootItem(){
		throwIfEmpty(); //throws exception if no elements
		
		Comparable toReturn = heapArray[0];
		heapArray[0] = heapArray[--size];
		
		bubbleDown();
		
		return toReturn;
	}

	private void throwIfEmpty(){
		if(this.size() == 0){
			throw new NoSuchElementException("No Elements in heap.");
		}
	}
	
	private void printArray(){
		
		for(int index=0; index<this.size(); index++){
			System.out.println(
					"heapArray[" + index + "] = " + heapArray[index]);
		}
		
		System.out.println();
	}
	
	/**
	 * Used for internal testing purposes.
	 * @param aaarg Not used.
	 */
	public static void main(String[] aaarg){
		final Random random = new Random();
		final String[] CATEGORY = { 
				"Life-threatening", "Chronic", "Major fracture", "Walk-in"
		};

		class Patient extends ER_Patient{		

			public Patient() {
				super(CATEGORY[random.nextInt(CATEGORY.length)]);
			}
		}

		class Util{

			//test if all of Heaps methods which say it's empty work
			public boolean hasNoneTest(Heap emptyHeap){				
				if(emptyHeap.size != 0 || emptyHeap.size() != 0 
						|| !emptyHeap.isEmpty()){
					
					return false;
				}
				
				try{
					emptyHeap.getRootItem();

					return false;
				} catch(NoSuchElementException x){
					//passes
				}

				try{
					emptyHeap.removeRootItem();
					
					return false;
				} catch(NoSuchElementException x){
					//passes
				}

				return true;
			}
		}

		//test increaseSize()
		Heap heap = new Heap();

		for(int index=0; index<2001; index++){
			heap.insert(index);
		}
		
		if(heap.size() != 2001 && heap.heapArray.length > Heap.SIZE){
			System.out.println("increaseSize() failed");
		}
		
		//insert and check if Objects come out in the right order
		heap = new Heap();
		
		for(int index=10; index>0; index--){
			heap.insert(index);
		}
		
		for(int index=1; index<=10; index++){		
			int integer = ((Integer)heap.removeRootItem()).intValue();
			
			if(integer != index){
				System.out.println("Failed heap order. Expected " + index 
						+ " got " + integer);
			}
		}
		
		if(heap.size() != 0){
			System.out.println("Failed heap's size() after extracting all objects");
		}
		
		//check all empty tests
		if(!new Util().hasNoneTest(heap)){
			System.out.println("Failed test for empty heap after 1 - 10 " 
					+ "emptied.");
		}
		
		//insert 100 Patients (ER_Patients sub class)
		for(int index=1; index<=100; index++){		
			heap.insert(new Patient());
		}
		
		try{
			if(((ER_Patient)heap.removeRootItem()).getSymptomCategory() 
					!= CATEGORY[0]
					&& ((ER_Patient)heap.removeRootItem()).getSymptomCategory() 
					!= CATEGORY[0]
					&& ((ER_Patient)heap.removeRootItem()).getSymptomCategory() 
					!= CATEGORY[0]){

				System.out.println(
						"Failed extracting Patients with correct priority");
			}
		} catch(NoSuchElementException x){
			System.out.println("Failed removeRootItem() for Patient.");
		}
		
		//try out getRootItem() when none added
		try{
			new Heap().getRootItem();
			throw new Throwable();
		} catch(NoSuchElementException x){
			//test checks out
		} catch(Throwable x){
			System.out.println("Didn't catch NoSuchElementException "
					+ "in getRootItem().");
		}

		//try out removeRootItem() when none added
		try{
			new Heap().removeRootItem();
			throw new Throwable();
		} catch(NoSuchElementException x){
			//test checks out
		} catch(Throwable x){
			System.out.println("Didn't catch NoSuchElementException "
					+ "in getRootItem().");
		}
		
		//insert and delete single object in new heap and see its empty
		heap = new Heap();
		
		if(!new Util().hasNoneTest(heap)){
			System.out.println("Failed test for empty heap before inserted.");
		}

		try{
			heap.insert(123);
			heap.removeRootItem();
		} catch(NoSuchElementException x){
			System.out.println(
					"Failed removal after inserting single element.");
		}
		
		if(!new Util().hasNoneTest(heap)){
			System.out.println("Failed test for empty heap after emptied.");
		}
	}	
}