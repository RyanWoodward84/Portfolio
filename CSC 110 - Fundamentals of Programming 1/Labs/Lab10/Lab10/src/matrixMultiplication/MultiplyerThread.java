package matrixMultiplication;

public class MultiplyerThread extends Thread
{
	private int row;
	private int column;
	private MatrixMultiplyer matrix;
	
	public MultiplyerThread (int rowInput, int columnInput, MatrixMultiplyer matrixInput)
	{
		row = rowInput;
		column = columnInput;
		matrix = matrixInput;
	}
	
	public void run()
	{
		matrix.multiplyOutputEntry(row, column);
	}
}
