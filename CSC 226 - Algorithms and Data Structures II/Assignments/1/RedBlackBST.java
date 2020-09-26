import java.util.NoSuchElementException;
import java.util.*;
import java.io.*;

/*
Ryan Woodward
V00857268
CSC 226, Summer 2019
June 1, 2018

Assignment 1

Modified the original RedBlackBST.java code from algs4 textbook in order to calculate the percentage of
red nodes in a RB-tree
*/
public class RedBlackBST {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;     // root of the BST

    // BST helper node data type
    private class Node {
        private int key;           // key
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int size;          // subtree count


        public Node(int key, boolean color, int size) {
            this.key = key;
            this.color = color;
            this.size = size;
        }
    }

	public RedBlackBST() {

	}
   /***************************************************************************
    *  Node helper methods.
    ***************************************************************************/
    // is node x red; false if x is null ?
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    } 


    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

   /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

   /***************************************************************************
    *  Red-black tree insertion.
    ***************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws NullPointerException if {@code key} is {@code null}
     */
    public void put(int key) {

        root = put(root, key);
        root.color = BLACK;
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, int key) { 
        if (h == null) return new Node(key, RED, 1);

        int cmp = key - h.key;
        if      (cmp < 0) h.left  = put(h.left,  key); 
        else if (cmp > 0) h.right = put(h.right, key); 
        else              h.key   = key;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }
	
	public double percentRedHelper(Node percent) {
		double red = 0;
		if (percent == null)
			return 0;
		
		red += percentRedHelper(percent.left);
		red += percentRedHelper(percent.right);

		if (isRed(percent))
			red++;
		return red;
	}
	
	public double percentRed() {
		double red = percentRedHelper(root);
		double percentRed = ((red / root.size) * 100.0);
		return percentRed;
	}


   /***************************************************************************
    *  Red-black tree helper functions.
    ***************************************************************************/

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }


    /**
     * Unit tests the {@code RedBlackBST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) throws FileNotFoundException { 
		RedBlackBST st = new RedBlackBST();
		double percentRed;
		if (args.length != 0)
		{
			File file = new File(args[0]);
			String filename = args[0];
		
			if (file.exists())
			{

				Scanner input = new Scanner(file);
				System.out.println("Reading from file: " + filename);
				while (input.hasNextInt())
				{
					st.put(input.nextInt());
				}
				input.close();
				//System.out.printf("Size of tree is %d\n", st.size());
				
				percentRed = st.percentRed();
				System.out.println("Percent of Red Nodes: " + percentRed);
			}
		}
	
		else 
		{
			Random rand = new Random();
			
			//Red Black Tree 10^4 100 trials
			RedBlackBST n4 = new RedBlackBST();
			double averagePer4 = 0;
			for (int j = 0; j < 100; j++)
			{
				
				while (n4.size() < Math.pow(10,4))
				{
					n4.put((int)rand.nextInt((int)Math.pow(10,4)));
				}
				averagePer4 += n4.percentRed();
				
			}
			
			System.out.printf("Size of tree is %d\n", n4.size());
			System.out.println("Percent of Red Nodes: " + n4.percentRed());
			
			//Red Black Tree 10^5 100 trials
			RedBlackBST n5 = new RedBlackBST();
			double averagePer5 = 0;
			for (int j = 0; j < 100; j++)
			{
				while (n5.size() < Math.pow(10,5))
				{
					n5.put((int)rand.nextInt((int)Math.pow(10,5)));
				}
				averagePer5 += n5.percentRed();
			}

			System.out.printf("Size of tree is %d\n", n5.size());
			System.out.println("Percent of Red Nodes: " + n5.percentRed());
			
			//Red Black Tree 10^6 100 trials
			RedBlackBST n6 = new RedBlackBST();
			double averagePer6 = 0;
			for (int j = 0; j < 100; j++)
			{
				while (n6.size() < Math.pow(10,6))
				{
					n6.put((int)rand.nextInt((int)Math.pow(10,6)));
				}
				averagePer6 += n6.percentRed();
			}

			System.out.printf("Size of tree is %d\n", n6.size());
			System.out.println("Percent of Red Nodes: " + n6.percentRed());		
		}
    }

        
 
}

/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
