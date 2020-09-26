/*
Ryan Woodward
V00857268
CSC 225 - Programming Assignment 1
Sept 23, 2017
*/

import java.util.*;
import java.io.*;
public class LinkedList
{
	int n;
	ListNode start;
	ListNode rear;

	//Empty Constructor
	public LinkedList()
	{
		n= 0;
		start= null;
		rear= null;
	}
	public LinkedList(int size, ListNode first, ListNode last)
	{
		n= size;
		start= first;
		rear= last;
	}

	public static LinkedList readBigInteger(Scanner in)
	{
		LinkedList x;
		x= new LinkedList(); // You can move this statement.

		x.n = readInteger(in);
		if (x.n <= 0)
			x =  null;
	
		if (x != null)
		{
			for (int i = 0; i < x.n; i++)
			{
				ListNode node = new ListNode(readInteger(in), x.start);
				if (i == 0)
					x.rear = node;
				x.start = node;
			}	
		}
	  

		return(x);
	}
	public void printBigInteger()
	{
		// Question 2 code goes here.
		if (this.n == 0)
		return;
		this.reverse(0);
		boolean leadingZero = false;
		ListNode current = this.start;
		if (current.data == 0)
			leadingZero = true;
		while (current != null)
		{
			if (!leadingZero)
				System.out.print (current.data);
			if (current.next != null && current.next.data !=0)
				leadingZero = false;
			if (current.next == null && leadingZero == true)
				System.out.print(current.data);
			current = current.next;
		}
	
		this.reverse(0);
	}
	public void reverse(int level)
	{
		Test.checkList(this); // Do not remove or move this statement.

		// Question 3 code goes here.
		if (this.n <= 1)
		return;
		LinkedList left = new LinkedList();
		LinkedList right = new LinkedList();
	   
		left.n = this.n / 2;
		right.n = this.n / 2;
	   
		if (this. n % 2 != 0) // This will make sure that if n is odd that the right list will have the correct n value
			right.n++;
	   
		//This block of code will place the correct values of start and rear
		//for the the split LinkedLists left and right
		left.start = this.start;
		left.rear = this.start;
		for (int i = 1; i < left.n; i++)
		{
			left.rear = left.rear.next;
		}
		right.start = left.rear.next;
		right.rear = this.rear;
		left.rear.next = null;
	   
	//Now going to reverse the list if down to 2
	//otherwise going to do another split call (reverse call)
	//Note: We don't need to worry about a list of size 1 as it will
	//		never enter either a recursive call nor a split statement
	
	//Left Linked List Block
	
	if (left.n == 2)
	{
		left.rear.next = left.start;
		left.start.next = null;
		left.start = left.rear;
		left.rear = left.rear.next;
	}
	
	//Here is the recursive call for the left side
	if (left.n > 2)
		left.reverse(level + 1);
	
	//Right Linked List block
	
	if (right.n == 2)
	{
		right.rear.next = right.start;
		right.start.next = null;
		right.start = right.rear;
		right.rear = right.rear.next;
	}
	
	if (right.n > 2)
		right.reverse(level + 1);
	
	//Now for the concatenation of the two lists
	
	right.rear.next = left.start;
	this.start = right.start;
	this.rear = left.rear;
	
	return;
	
   }
   public void plus_plus()
   {
	
		// Question 4 code goes here.
		if (this.n == 0)
		   return;
		if (this.start.data + 1 > 9)
		{
			
			ListNode current = this.start;
			while (current != null)
			{
				current.data = (current.data + 1) % 10;
				
				if (current.next == null && current.data == 0)
				{
					this.n++;
					ListNode node = new ListNode(1, null);
					current.next = node;
					this.rear = node;
					return;
				}

				if (current.data == 0)
					current = current.next;
				else
					return;
			}
		}
		else
		{
			this.start.data++;
		}
   }
   public LinkedList plus(LinkedList y)
   {
       LinkedList z;

       z= new LinkedList();

       // Question 5 code goes here.
	   
		if (this.n == 0 || y.n == 0 || this == null || y == null)
		   return null;

		int carry = 0;
		int sumZ = 0;
		ListNode currentX = this.start;
		ListNode currentY = y.start;
		int xData = 0;
		int yData = 0;
	   
		while (currentX != null || currentY != null || carry == 1)
		{
			if (currentX != null)
				xData = currentX.data;
		   
			if (currentY != null)
				yData = currentY.data;
			   
			z.n++;
			ListNode nodeZ = new ListNode(0, null);
			if (currentX != null || currentY != null || carry == 1)
			{
				sumZ = xData + yData + carry;
				carry = 0; //Must change carry to zero as it's been used in the above equation
				xData = 0;
				yData = 0;
				nodeZ.data = sumZ % 10;
				if (sumZ > 9)
					carry = 1;
				if (z.n == 1)
				{
					z.start = nodeZ;
					z.rear = z.start;
				}
				else
				{
					nodeZ.next = z.start;
					z.start = nodeZ;	  
				}
				 
				if (currentX != null)
					currentX = currentX.next;
				if (currentY != null)
					currentY = currentY.next; 
			}
			
			
		}
		z.reverse(0);
		return(z);
	}

// You can use these routines for this assignment:

// Tries to read in a non-negative integer from the input stream.
// If it succeeds, the integer read in is returned. 
// Otherwise the method returns -1.
	public static int readInteger(Scanner in)
	{
		int n;

		try{
			n= in.nextInt();
			if (n >=0) return(n);
			else return(-1);
		}
		catch(Exception e)
		{
//        We are assuming legal integer input values are >= zero.
			return(-1);
		}
	}

// Use this for debugging only.

	public void printList()
	{
		ListNode current;

		int count=0;

		current= start;

		while (current != null)
		{
			count++;
           
			System.out.println("Item " + count + " in the list is " 
                            + current.data);
			current= current.next;
		}
	}
}
