/*
 * Ryan Woodward
 * Assignment 5: Producer Consumer Threading
 * Comp 3411
 */

/*
 * Reference List
 *
 * The following is a list of websites used to help learn the design. Initially I had
 * my producer consumer in the BoundedBuffer static class. But I learned that if each
 * method was synchronized that there could potentially be a race condition. Hence I
 * moved them to the main method.
 *
 * https://en.wikipedia.org/wiki/Producer%E2%80%93consumer_problem
 * https://stackoverflow.com/questions/20906548/why-is-synchronized-block-better-than-synchronized-method
 * https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
 * https://www.geeksforgeeks.org/synchronized-in-java/
 */

import java.util.LinkedList;

public class ProdCons
{
	public static class BoundedBuffer
	{
		final int BUFFER_SIZE = 5;
		
		//Able to take this out as the definitions of producer and consumer
		//were moved to the main method
		//private int count, in, out;

		LinkedList<Integer> buffer = new LinkedList<Integer>();

		//The class was changed, no need to keep this constructor anymore
		/*
		public BoundedBuffer()
		{
			count = 0;
			in = 0;
			out = 0;
			buffer = new LinkedList<Integer>();
		}
		*/
		
		public void produce() throws InterruptedException
		{	
			int in = 0;
			int count = 0;
			while (count < 25)
			{
				//Large change here. Made this block synchronized as opposed to the
				//entire method. Turns out the synchronized method would put a lock
				//on the entire object, which was leading to a deadlock issue
				synchronized (this)
				{
					while (buffer.size() == BUFFER_SIZE)
					{
						try { System.out.println("*****Buffer now full*****"); wait();}
						catch (InterruptedException ie) {}
					}
					buffer.add(in);
					System.out.println("Produced: " + in);
					in++;

					//This could be added again if we wanted to keep a limit on
					//how many items are produced
					count++;
		
					notify();
	
					Thread.sleep(10);
				}
			}	
		}
	
		public void consume() throws InterruptedException
		{
			int consumeCount = 0;
			int itemRemoved;
			while(consumeCount < 25)
			{
				synchronized(this)
				{
					while (buffer.size() == 0)
					{
						try { System.out.println("*****Buffer now empty*****"); wait(); }
						catch (InterruptedException ie) {}
					}
		
					itemRemoved = buffer.removeFirst();
					
					System.out.println("Consumed: " + itemRemoved);
					
					consumeCount++;
					notify();
	
					Thread.sleep(10);
				}
			}
		}	
	}	
/*
 * These methods have been replaced and placed in the main method.
 * In doing so we were able to synchronize each method, which would
 * then avoid the object becoming deadlocked
 */
	/* 
	public static class producerThread implements Runnable
	{
		BoundedBuffer bufProd;
		producerThread(BoundedBuffer buf)
		{
			bufProd = buf;
		}
		public void run()
		{
			bufProd.produce();
			//try { bufProd.produce(); }
			//catch (InterruptedException ie) {}	
		}
	}
		
	public static class consumerThread implements Runnable
	{
		BoundedBuffer bufCons;
		consumerThread(BoundedBuffer buf)
		{
			bufCons = buf;
		}
		public void run()
		{
			bufCons.consume();
		}
	}
	*/
	public static void main(String[] args) throws InterruptedException
	{
		//This had to be made final due to calling it from within the Inner class
		final BoundedBuffer buf = new BoundedBuffer();
		

		//Thread producer = new Thread(new producerThread(buf));
		//Thread consumer = new Thread(new consumerThread(buf));	

		//After consulting professor it was determine threads need to be created
		//and defined within main method
		
		Thread producer = new Thread(new Runnable()
		{
			public void run()
			{
				//Recommended to use try and catch
				try { buf.produce(); }
				catch (InterruptedException ie) {;}
			}
		});

		Thread consumer = new Thread (new Runnable()
		{
			public void run()
			{
				try { buf.consume(); }
				catch (InterruptedException ie) {;}
			}
		});

		producer.start();
		consumer.start();

		//Join needed to be added
		producer.join();
		consumer.join();
			
	}
}
