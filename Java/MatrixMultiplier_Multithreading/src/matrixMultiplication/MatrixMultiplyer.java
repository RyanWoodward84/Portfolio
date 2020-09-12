package matrixMultiplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MatrixMultiplyer 
{
	private int [][] inputMatrix1;
	private int [][] inputMatrix2;
	private int [][] outputMatrix;
	
	/**
	 * Creates two integer matrixes from files, and establishes the size of the output matrix, for when the two other
	 * matrixes are multiplied
	 * @param inputFileMatrix1 The path of the file containing matrix1
	 * @param inputFileMatrix2 The path of the file containing matrix2
	 * @throws Exception
	 */
	public MatrixMultiplyer (String inputFileMatrix1, String inputFileMatrix2) throws Exception
	{
		int inputMatrix1rows = matrixRows(inputFileMatrix1);
		int inputMatrix1cols = matrixColumns(inputFileMatrix1);
		
		int inputMatrix2rows = matrixRows(inputFileMatrix2);
		int inputMatrix2cols = matrixColumns(inputFileMatrix2);
		
		if (inputMatrix1rows == inputMatrix2cols)
		{
			inputMatrix1 = new int[inputMatrix1rows][inputMatrix1cols];
			inputMatrix2 = new int[inputMatrix2rows][inputMatrix2cols];
		
			parseMatrix(inputMatrix1, inputFileMatrix1);
			parseMatrix(inputMatrix2, inputFileMatrix2);
			
			outputMatrix = new int[inputMatrix1rows][inputMatrix2cols];
			
		}
		else
		{
			System.out.println("The 2 input matrices are unable to perform Matrix Multiplication. Construction cancelled");
			System.out.println("The two matrices are initialized to 0");
			inputMatrix1 = new int[0][0];
			inputMatrix2 = new int[0][0];
		}
		
	}
	
	/**
	 * Can be called by individual threads in order to multiply the rows and columns of a matrix in parallel, the
	 * specific row and column of the output matrix to be calculated are given as arguments
	 * @param row The row of the output matrix
	 * @param column The column of the output matrix
	 */
	public void multiplyOutputEntry(int row, int column)
	{
		int result = 0;
		
		for (int i = 0; i < inputMatrix1.length; i++)
		{
			result += (inputMatrix1[row][i] * inputMatrix2[i][column]);
		}
	}
	
	/**
	 * Searches through the text file inputFile, which contains a valid matrix, and inputs the values into the inputMatrix
	 * @param inputMatrix The input matrix to be created
	 * @param inputFile The path to the input file containing the matrix
	 */
	private void parseMatrix(int [][] inputMatrix, String inputFile) throws FileNotFoundException
	{
		File inFile = new File(inputFile);
		Scanner in = new Scanner(inFile);
		
		int matrixRows = matrixRows(inputFile);
		int matrixCols = matrixColumns(inputFile);
		
		for (int i = 0; i < matrixRows; i++)
		{
			for (int j = 0; j < matrixCols; j++)
			{
				inputMatrix[i][j] = in.nextInt();
			}
		}
		
		//Just Printing the original matrices
		for (int i = 0; i < matrixRows; i++)
		{
			for (int j = 0; j < matrixCols; j++)
			{
				System.out.print(inputMatrix[i][j] + " ");
			}
			System.out.println();
		}
		
		in.close();
		
	}
	
	/**
	 * Searches a valid Matrix file for the number of rows in the matrix.
	 * @param inputFile The name of the input file
	 * @return Returns the number of rows in the matrix
	 */
	private int matrixRows(String inputFile) throws FileNotFoundException
	{
		File inFile = new File(inputFile);
		Scanner in = new Scanner(inFile);
		int rowCount = 0;

		while (in.hasNextLine())
		{
			if (fileContainsValidMatrix(inputFile))
			{	
				String row = in.nextLine();
				rowCount++;
			}
		}
		
		in.close();
		return rowCount;
	}
	/**
	 * Searches a valid matrix file for the number of columns in the matrix.
	 * @param inputFile The path of the input file of the matrix
	 * @return Returns the number of columns of the matrix as an integer
	 */
	private int matrixColumns(String inputFile) throws FileNotFoundException
	{
		File inFile = new File(inputFile);
		Scanner in = new Scanner(inFile);
		int colCount = 0;
		
		if(fileContainsValidMatrix(inputFile))
		{
		String columnCounter = in.nextLine();
		
			for(int i = 0; i < columnCounter.length(); i++)
			{
				if (!columnCounter.substring(i, i+1).equals(" ") )
				{
					colCount++;
				}
			}
		}
		
		return colCount;
		
	}

	/**
	 * Searches through an input file to determine whether or not the input file contains a valid matrix
	 * @param matrixFilePath The path to the file being read as a String type
	 * @return Returns true if the file contains valid matrices, false otherwise
	 */
	
	private boolean fileContainsValidMatrix (String matrixFilePath) throws FileNotFoundException
	{
		File inFile = new File(matrixFilePath);
		Scanner in = null;
		
		try
		{
			in = new Scanner(inFile);
			
			while (in.hasNextLine())
			{
				if (in.hasNextInt())
				{
					int test = in.nextInt();
				}
				else
				{
					System.out.println("This file contains non integers");
					return false;
				}
			}
			return true;
		}
		catch (FileNotFoundException exception)
		{
			System.out.println("File not found");
		}
		
		finally
		{
			if (in != null)
			{
				in.close();
			}
		}
		
		return true;
	}
	
/*
	public void printOutput()
	{

	}
*/
	public int getOutputMatrixRows()
	{
		return outputMatrix[0].length;
	}
	
	public int getOutputMatrixColumns()
	{
		return outputMatrix.length;
	}
}
