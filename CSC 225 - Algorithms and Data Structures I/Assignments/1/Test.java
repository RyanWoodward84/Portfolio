import java.util.*;
import java.io.*;
// This program tests the routines students write for assignment
// 1 Part B for CSC 225 in Fall 2013.

// It is up to the student to ensure
// that the input is read in properly and that their program
// computes the correct results.
public class Test
{
// Insert code here to test the integrity of a list
// (the values for n, start and rear should be correct). 
// I would normally place checkList in the LinkedList class
// but we put it here so we can replace your code with
// our own when doing the automated testing.
   static void checkList(LinkedList x)
   {
       // Insert your code here.
       // Print an error message and stop if there is anything
       // wrong with the list.
   }
   public static void main(String args[])
   {
       int n_times;
       int xv;
       LinkedList x, y, z;
       int problem_number;
       int i;
 
//     First test the increment method.

       x= new LinkedList();
       x.start= new ListNode(0, null);
       x.rear= x.start;
       x.n=1;
       checkList(x);
       xv=0;
       System.out.println("Test the increment method:");
       for (n_times= 1; n_times <= 10000; n_times*= 10 )
       {
           test_plus_plus(n_times, xv, x); 
           xv+= n_times;
       }

// Now test the addition method.

       problem_number=0;
       Scanner in = new Scanner(System.in);

       x= LinkedList.readBigInteger(in);
       while (x != null)
       {
           checkList(x);
           problem_number++;

           y= LinkedList.readBigInteger(in);
           if (y == null)
           {
               throw new RuntimeException("Error- failed to read in second Big Integer y");
           }
           checkList(y);

           z= x.plus(y);
           checkList(z);

           System.out.println("Problem " + problem_number + ":");
           x.printBigInteger();
           System.out.print(" + ");
           y.printBigInteger();
           System.out.print(" = ");
           z.printBigInteger();
           System.out.println();

           checkList(x);
           checkList(y);
           checkList(z);
           x= LinkedList.readBigInteger(in);
       }
   }
   public static void test_plus_plus(int num_times, int xv, LinkedList x)
   {
      int i;
   
      xv+= num_times;
      for (i=0; i < num_times; i++)
      {
              x.plus_plus();
              checkList(x);
      }
      System.out.print("Value should be " + xv + " is ");
      x.printBigInteger();
      System.out.println();
   }
}
