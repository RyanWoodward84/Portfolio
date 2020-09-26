/*
   The Test class is provided to you for CSC 225
   by Wendy Myrvold.  You do not have permission to distribute
   this code or to use it for any other purpose.

   Make sure that your program runs with this version
   of test (with the checkList code included).

*/


import java.util.*;
import java.io.*;

public class Test
{
/*
   This checkList method returns true if the list
   is valid and otherwise it prints an error message
   and returns false.

   Warning: students who did not write code to check
   their lists often had problems with them.
   You are strongly advised to check your lists
   since if your lists are not correct your code
   is not correct.
*/

   static boolean checkList(WeightedEdgeList x)
   {

   // Insert code here to test the integrity of a list.

      return(true);
   }

   public static void main(String args[])
   {
       WeightedEdgeList G, tree;
       int graph_num;

//     Use for checking the final spanning tree.

       Scanner in = new Scanner(System.in);

       graph_num=0;

       G= WeightedEdgeList.readWeightedEdgeList(in);
	   
	   

       while (G!= null)
       {
           graph_num++;
           System.out.println("Graph: " + graph_num);
           G.printWeightedEdgeList();

           checkList(G);

           G.edgeSort();
           checkList(G);

           System.out.println("After sorting the edges by weight:");
           G.printWeightedEdgeList();

           tree= G.minWeightTree();

//         You can return null if you have no code for this.
		
           if (tree != null) 
           {
               System.out.println("The minimum weight tree:");
               tree.printWeightedEdgeList();
    
               System.out.println("The chords for this tree:");
               G.printWeightedEdgeList();

               checkList(G);
               checkList(tree);
           }

           G= WeightedEdgeList.readWeightedEdgeList(in);
       }
	 
    }
}
