import java.util.*;
import java.io.*;

public class MyTest
{
	public static void main(String args[]) throws FileNotFoundException
	{
		Scanner in = new Scanner(new File("TestMe.txt"));
		
		LinkedList list1 = new LinkedList();
		LinkedList list2 = new LinkedList();
		LinkedList list3 = new LinkedList();
		list1 = list1.readBigInteger(in);
		list2 = list2.readBigInteger(in);
		
		//Testing out readBigInteger
		System.out.println("Testing out readBigInteger");
		list1.printList();

		
		//Testing printBigInteger
		System.out.println("Testing printBigInteger");
		list1.printBigInteger();
		System.out.println();
		
		
		//Testing out reverse
		System.out.println("Testing out reverse");
		list1.reverse(0);
		list1.printBigInteger();
		list1.reverse(0);
		System.out.println();
		
		
		//Testing plus_plus
		
		System.out.println("Testing plus_plus");
		list1.plus_plus();
		list1.printBigInteger();
		System.out.println();
		
		
		//Testing out plus_plus
		System.out.println("Testing plus");
		System.out.print("List x: ");
		list1.printBigInteger();
		System.out.println();
		System.out.print("List y: ");
		list2.printBigInteger();
		System.out.println();
		list3 = list1.plus(list2);
		System.out.print("List z: ");
		list3.printBigInteger();
		
		in.close();
	}
}