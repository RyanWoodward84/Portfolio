/*
	Ryan Woodward
	V00857268
	Nov 25, 2017
	CSC 225 - Assignment 5
*/


/*
   The WeightedEdgeNode class is provided to you for CSC 225
   by Wendy Myrvold.  You do not have permission to distribute
   this code or to use it for any other purpose.

   You should add code for edgeSort and minWeightTree.
   Otherwise, do not modify the code.

*/

import java.util.*;
import java.io.*;
public class WeightedEdgeList
{
    public static boolean debug= true; // Change to false before submitting.

    int n;
    int m;
    WeightedEdgeNode start;
    WeightedEdgeNode rear;

    public void edgeSort()
    {
        // Insert your code here.
		
		// First need to do a linear scan in order to find the maximum edge weight
		
		WeightedEdgeNode current;
		current = this.start;
		int maxEdgeWeight = 0;
		
		//Getting the maximum weight for the radixArray
		for (int b = 2; b >= 0; b--)
		{
			while (current != null)
			{
				if (maxEdgeWeight < current.edge[b])
					maxEdgeWeight = current.edge[b];
				current = current.next;
			}
			current = this.start;
		}

		//Here comes the radix sort
		
		current = this.start;
		WeightedEdgeList[] radixArray = new WeightedEdgeList[maxEdgeWeight + 1];
		
		//Initializing radixArray entries to null
		for (int a = 0; a < maxEdgeWeight; a++)
		{
			radixArray[a] = null;
		}
		
		//Need to analyze edge[2], edge[1], edge[0] in that order to get a proper sorted
		//May have to adjust vertex and edge count but may not matter

		for (int d = 2; d > -1 ; d--)
		{
			int sortWeight = 0;
			//Linear Pass to place Nodes in radix array buckets
			while (current != null)
			{
				sortWeight = current.edge[d];
				if (radixArray[sortWeight] == null)
				{
					radixArray[sortWeight] = new WeightedEdgeList(0, 0, current, current);
					current = current.next;
					radixArray[sortWeight].rear.next = null;
				}
				
				else
				{
					radixArray[sortWeight].rear.next = current;
					radixArray[sortWeight].rear = radixArray[sortWeight].rear.next;
					current = current.next;
					radixArray[sortWeight].rear.next = null;
				}
			}
			
			//Concatenating the buckets together
			current = null;
			for (int c = 0; c < maxEdgeWeight + 1; c++)
			{
				if (radixArray[c] != null)
				{
					if (current == null)
					{
						current = radixArray[c].rear;
						
						
						this.start = radixArray[c].start;
						this.rear = current;
						
					}
					else
					{
						current.next = radixArray[c].start;
						current = radixArray[c].rear;
						this.rear = current;
					}
						
				}
			}
			
			//Reinitialize the radixArray to null for the next run
			for (int i = 0; i < maxEdgeWeight +1; i++)
			{
				radixArray[i] = null;
			}
			
			//Reset current to the start of the new list
			current = this.start;
			
		}
    }

/*  You should assume this method is called with the edges
    already sorted by weight. The two tasks have been 
    separated so that you can get part marks if one of your
    methods works but the other one does not.
*/
    public WeightedEdgeList minWeightTree()
    {
		WeightedEdgeNode current;
		WeightedEdgeList minTree = new WeightedEdgeList(0, 0, null, null);
		//Get current to look at the start of the list
        current = this.start;
		
		//Use Union/Find to create a parent matrix to store the edges
		UnionFind smallestVertex = new UnionFind(this.n);
		
		//Using a side vertex matrix to determine if added (-1 weight if not)
		int[] addedVertex = new int[this.n];
		for (int i = 0; i < n; i++)
		{
			addedVertex[i] = -1;
		}
		
		
		//Traverse through the list
		while (current != null)
		{
			
			//Need to check if the vertices of the edge be analyzed are
			//in the same component or not
			if (!smallestVertex.same_component(current.edge[1], current.edge[2]))
			{
				//First edge being added to minTree
				if (minTree.start == null)
				{
					//add and update minTree components
					minTree.start = current;
					minTree.rear = current;
						
					smallestVertex.union(current.edge[1], current.edge[2]); //Updating parent matrix
					current = current.next;
					minTree.rear.next = null;
					minTree.m++; //edge added update
						
					//These if statements check if new vertices have been added
					if (addedVertex[current.edge[1]] < 0)
					{
						addedVertex[current.edge[1]]++;
						minTree.n++;
					}
					if (addedVertex[current.edge[2]] < 0)
					{
						addedVertex[current.edge[2]]++;
						minTree.n++;
					}
						
					//Need to remove node from original list
					
					this.start = current;
					this.rear = this.start;
					this.m--;
					
				
				}
					
				else
				{
					
					//add and update minTree components
					minTree.rear.next = current;
					minTree.rear = minTree.rear.next;
						
					smallestVertex.union(current.edge[1], current.edge[2]); //Updating parent matrix
					
					minTree.m++;
					
					//These if statements check if new vertices have been added
					if (addedVertex[current.edge[1]] < 0)
					{
						addedVertex[current.edge[1]]++;
						minTree.n++;
					}
					if (addedVertex[current.edge[2]] < 0)
					{
						addedVertex[current.edge[2]]++;
						minTree.n++;
					}
					
					//Eliminating the node from original tree  *****************
					if (this.start == current)
					{
						current = current.next;
						this.start = current;
						this.rear = current;
						this.m--;
						
					}
					else
					{
						WeightedEdgeNode temp = this.rear;
						current = current.next;
						this.rear = current;
						temp.next = this.rear;
					}
					
					
					minTree.rear.next = null;
					
				}

			}
			else
			{
				current = current.next;
			}
			
			
		}

        return(minTree); 
    }

// Do not make any changes to code below this line:
//----------------------------------------------------------//

    public WeightedEdgeList(int num_vertex, int num_edge, 
              WeightedEdgeNode start_node, WeightedEdgeNode rear_node)
    {
        n=num_vertex;
        m=num_edge;
        start= start_node;
        rear= rear_node;
    }

    public WeightedEdgeList()
    {
        n=0;
        m=0;
        start= null;
        rear= null;
    }

/*  
    The input format consists of two integers
    n m 
    where 
    n is the number of nodes of the graph, and
    m is the number of edges of the graph.
    Legal edge weights are always greater than or equal to 1.
    Then for each edge (u, v) with weight w the input contains:
    w  u  v
*/

    public static WeightedEdgeList readWeightedEdgeList(Scanner in)
    {
        WeightedEdgeList G;
        WeightedEdgeNode add_node;

        int num_edge;
        int w, u, v;
        int i;

        if (! in.hasNextInt()) return null;

        G= new WeightedEdgeList();
        G.n= in.nextInt();

        if (! in.hasNextInt())
        {
           System.out.println("Missing value for m.");
           return(null);
        }

        num_edge= in.nextInt();

        if (debug) System.out.println("n= " + G.n + "  m= " + num_edge);

        for (i=0; i < num_edge; i++)
        {
            if (! in.hasNextInt())
            {
               System.out.println("Error- Missing weight for edge " + (i+1));
               return(null);
            }
            w= in.nextInt();
            if (w < 1)
            {
               System.out.println("Error- Invalid edge weight " + w);
               return(null);
            }

            if (! in.hasNextInt())
            {
               System.out.println("Error- Missing u for edge " + (i+1));
               return(null);
            }
            u= in.nextInt();

            if (! in.hasNextInt())
            {
               System.out.println("Error- Missing v for edge " + (i+1));
               return(null);
            }
            v= in.nextInt();

            if (u < 0 || v < 0 || u >= G.n || v >= G.n)
            {
                System.out.println("Error- Invalid edge: " + u + " " + v);
                return(null);
            } 

            add_node= new WeightedEdgeNode(w, u, v, null);
            G.addRear(add_node);
        }
        return(G);
    }
    public void addRear(WeightedEdgeNode add_node)
    {
        if (start == null)
        {
            start= add_node;
        }
        else
        {
            rear.next= add_node;
        }
        rear= add_node;
        rear.next= null;
        m++;
    }

    public void printWeightedEdgeList()
    {
        WeightedEdgeNode current;

        int n_per_line=5;
        int num;
        int i;

        current= start;
        num=0;
        while (current != null)
        {
            System.out.format("[%3d (%3d, %3d)] ", current.edge[0], 
                                      current.edge[1], current.edge[2]);
            num++;
            if (num== n_per_line)
            {
               System.out.println();
               num=0;
            }
            current= current.next;
        }
        if (num!= 0) System.out.println();
    }
}
