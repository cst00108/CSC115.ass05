/*
 * Name:      Jason Donald
 * ID:        V00861539
 * Date:      2006-04-04
 * Filename:  PriorityQueue.java
 * Details:   CSC115 Assignment 5
 */

import java.util.NoSuchElementException;
import java.util.Random;

public class PriorityQueue {
	
	private Heap heap;

	/**
	 * Creates an empty priority queue.
	 */
	public PriorityQueue() {
		heap = new Heap();
	}

	/**
	 * Inserts an item into the queue.
	 * @param item The item to insert.
	 */
	public void enqueue(Comparable item){
		heap.insert(item);
	}
	
	/**
	 * Removes the highest priority item from the queue.
	 * @return The item.
	 * @throws java.util.NoSuchElementException if the queue is empty.
	 */
	public Comparable dequeue(){
		return heap.removeRootItem();
	}
	
	/**
	 * Retrieves, but does not remove the next item out of the queue.
	 * @return The item with the highest priority in the queue.
	 * @throws java.util.NoSuchElementException if the queue is empty.
	 */
	public Comparable peek(){
		return heap.getRootItem();
	}
	
	/**
	 * @return True if the queue is empty, false if it is not.
	 */
	public boolean isEmpty(){
		return heap.isEmpty();
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
		
		try{
			new PriorityQueue().peek();
			System.out.println("Error - peeked with no elements");
		} catch(NoSuchElementException x){
			//
		} catch(Throwable x){
			System.out.println("Error - peeked with no elements");
		}
		
		//test increaseSize()
		PriorityQueue heap = new PriorityQueue();

		for(int index=0; index<2001; index++){
			heap.enqueue(index);
		}
		
		//insert and check if Objects come out in the right order
		heap = new PriorityQueue();
		
		for(int index=10; index>0; index--){
			heap.enqueue(index);
		}
		
		for(int index=1; index<=10; index++){		
			int integer = ((Integer)heap.dequeue()).intValue();
			
			if(integer != index){
				System.out.println("Failed heap order. Expected " + index 
						+ " got " + integer);
			}
		}
		
		//insert 100 Patients (ER_Patients sub class)
		for(int index=1; index<=100; index++){		
			heap.enqueue(new Patient());
		}
		
		try{
			if(((ER_Patient)heap.dequeue()).getSymptomCategory() 
					!= CATEGORY[0]
					&& ((ER_Patient)heap.dequeue()).getSymptomCategory() 
					!= CATEGORY[0]
					&& ((ER_Patient)heap.dequeue()).getSymptomCategory() 
					!= CATEGORY[0]){

				System.out.println(
						"Failed extracting Patients with correct priority");
			}
		} catch(NoSuchElementException x){
			System.out.println("Failed dequeue() for Patient.");
		}
		
		//try out dequeue() when none added
		try{
			new PriorityQueue().dequeue();
			throw new Throwable();
		} catch(NoSuchElementException x){
			//test checks out
		} catch(Throwable x){
			System.out.println("Didn't catch NoSuchElementException "
					+ "in dequeue().");
		}
	}
}
	
