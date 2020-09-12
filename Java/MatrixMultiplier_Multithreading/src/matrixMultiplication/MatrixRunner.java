package matrixMultiplication;

public class MatrixRunner 
{

	public static void main(String[] args) throws Exception
	{
		MatrixMultiplyer equation1 = new MatrixMultiplyer(args[0],args[1]);
		
		int outRows = equation1.getOutputMatrixRows();
		int outCols = equation1.getOutputMatrixColumns();

		System.out.println("Rows: " + outRows + "\tColumns: " + outCols);
		
		MultiplyerThread thread00 = new MultiplyerThread(0, 0, equation1);
		MultiplyerThread thread01 = new MultiplyerThread(0, 1, equation1);
		MultiplyerThread thread02 = new MultiplyerThread(0, 2, equation1);
		MultiplyerThread thread10 = new MultiplyerThread(1, 0, equation1);
		MultiplyerThread thread11 = new MultiplyerThread(1, 1, equation1);
		MultiplyerThread thread12 = new MultiplyerThread(1, 2, equation1);
		MultiplyerThread thread20 = new MultiplyerThread(2, 0, equation1);
		MultiplyerThread thread21 = new MultiplyerThread(2, 1, equation1);
		MultiplyerThread thread22 = new MultiplyerThread(2, 2, equation1);
		
		thread00.start();
	}

}
