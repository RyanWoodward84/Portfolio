/*
 * Ryan Woodward
 * V00857268
 * Comp 3411
 */


import java.util.LinkedList;

public class Synchro 
{

	public static void main(String[] args) throws InterruptedException
	{
	
		BoundedBuffer buf = new BoundedBuffer();
		int itemCount = 0;

		Thread threadProduce = new Thread(new Runnable()
		{
			public void run() 
			{
				buf.produce();
			//	try {buf.produce(); }
			//	catch(InterruptedException ex) 
			//	{ Thread.currentThread().interrupt(); }
				//catch (InterruptedException ie) { System.out.println(ie);}
			}
		});

		Thread threadConsume = new Thread(new Runnable()
		{
			public void run() 
			{
				buf.consume();
				//try {buf.consume();}
				//catch(InterruptedException ex)
                                //{ Thread.currentThread().interrupt(); }
				//catch (InterruptedException ie) { System.out.println(ie);}
			
			}
		});
	
		threadProduce.start();
		threadConsume.start();

		threadProduce.join();
		threadConsume.join();	
	}

	public class BoundedBuffer<E>
	{
		private int totalProduced;
		private int totalConsumed;
		private int count;

		private  LinkedList<Integer> buffer;
		private static final int bufferSize = 5;

		BoundedBuffer()
		{
			totalProduced = 0;
			totalConsumed = 0;
			count = 0;
			buffer = new LinkedList<Integer>();
		}
			
		public synchronized void produce()
		{
			System.out.println("In produce");
			while (buffer.size() == bufferSize)
			{ wait(); } 
			
				//try	{wait();}
				//catch	(InterruptedException ie) {}
			
			System.out.println("Produced item: " + count);
			buffer.add(count++);		
			totalProduced++;
	
			notify();
			//}
		}
		
		public synchronized void consume()
		{
			System.out.println("In consume");
			while (buffer.size() == 0)
			{ wait(); }
				//try	{wait();}
				//catch 	(InterruptedException ie) {}
	
			int item = buffer.removeFirst();
			System.out.println("Consumed item: " + item);
	
			totalConsumed++;
		
			notify();
			
				
			//}
		}			
	}
}	
