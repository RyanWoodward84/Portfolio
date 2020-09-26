/* Ryan Woodward
 *
 * Assignment 3 : Matrix Multiplier using Threads
 * Comp 3411
 */

import java.util.Random;
import java.util.Scanner;



public class MatrixMultiplier
{

	public static class Matrix
	{
		int a; //this is the width of the matrix
		int b; //this is the length of the matrix
		int[][] matrix;
		Matrix(int a, int b)
		{
			this.a = a;
			this.b = b;
			this.matrix = new int[a][b]; //initializing size of matrix
		}

		public void fill()
		{
			for (int i = 0; i < a; i++)
			{
				for (int j = 0; j < b; j++)
				{
					Random ran = new Random();
					matrix[i][j] = ran.nextInt((10 - 1) + 1) + 1;
				}
			}
		}	

		public int getRow()
		{ return this.a; }

		public int getCol()
		{ return this.b; }

		public int getCell(int x, int y)
		{ return this.matrix[x][y]; }
		
		public void changeCell(int x, int y, int value)
		{
			this.matrix[x][y] = value;
		}

		public void printMatrix()
		{
			System.out.println("******Matrix******");
			for (int i = 0; i < a; i++)
			{
				System.out.print("| ");
				for (int j = 0; j < b; j++)
				{
					System.out.print(matrix[i][j] + " ");		
				}
				System.out.println("|");
			}
			System.out.println("******************");
		}	
	}

	public static class Multiplier implements Runnable
	{
		int a;
		int b;
		static Matrix matOne;
		static Matrix matTwo;
		static Matrix matEnd;
		int length;
		

		Multiplier(Matrix mat1, Matrix mat2, Matrix matFinal, int x, int y)
		{
			this.a = x;
			this.b = y;
			matOne = mat1;
			matTwo = mat2;
			length = mat1.getCol();
			matEnd = matFinal;	
		}	
	
		public void run()
		{
			
			try { Thread.sleep(1000); }
			catch (InterruptedException ie) {}
					
	
			int i = 0;
			int total = 0;
			while (i < length)
			{
				total += matOne.getCell(a,i) * matTwo.getCell(i, b);
				i++;	
			}
			matEnd.changeCell(a,b,total);
			matEnd.printMatrix();	
		}
	}

	public static void main (String args[])
	{
		
		Scanner input = new Scanner (System.in);
		
		boolean matrixCheck = false;
		
		while(!matrixCheck)
		{
			// Grabbing user input in order to create generic matrices filled with
			// random integers between 1 - 9
			System.out.println("*********************************************");
			System.out.println("Please input the number of rows of Matrix 1: ");
			int row = input.nextInt();
			System.out.println("Please input the number of columns of Matrix 1: ");
			int col = input.nextInt();
		
			Matrix mat1 = new Matrix(row, col);
			mat1.fill();
			System.out.println("*** Matrix 1 randomly populated ***");
			mat1.printMatrix();

			System.out.println("\nPlease input the number of rows of Matrix 2: ");
			row = input.nextInt();
			System.out.println("\nPlease input the number of rows of Matrix 2: ");
			col = input.nextInt();
	
			Matrix mat2 = new Matrix (row,col);
			mat2.fill();
			System.out.println("*** Matrix 2 randomly populated ***");
			mat2.printMatrix();

			//Checking to ensure matrix multiplication is possible
			if (mat1.getCol() == mat2.getRow())
			{
				System.out.println("Matrix dimensions confirmed for Matrix Multiplication\n");
				matrixCheck = true;
			}
			else
			{
				System.out.println("ERROR: Matrix 1 columns must equal Matrix 2 Rows\n");
			}

			//The number of runner threads we will need is the total size of the matrix (i * j)
			
			Matrix matFinal = new Matrix(mat1.getRow(), mat2.getCol());

			Thread[][] threads = new Thread[mat1.getRow()][mat2.getCol()];
			for (int i = 0; i < mat1.getRow(); i++)
			{
				for (int j = 0; j < mat2.getRow(); j++)
				{
					threads[i][j] = new Thread(new Multiplier(mat1,mat2,matFinal,i,j));
					threads[i][j].start();
					
					//Not essential, the join slows it down, it still runs
					//sequentially. But that's ok for small matrices	
					try { threads[i][j].join(); }
					catch(InterruptedException ie) {}
				}
			}
		
			
		}
		input.close();
	}
}
