import java.util.LinkedList;

public class ProdCons
{
	public static class BoundedBuffer
	{
		private static final int BUFFER_SIZE = 5;
		
		private int count, in, out;
		private LinkedList<Integer> buffer;

		public BoundedBuffer()
		{
			count = 0;
			in = 0;
			out = 0;
			buffer = new LinkedList<Integer>();
		}
		
		public synchronized void produce()
		{	for (int i = 0; i < 10; i++) {
			while (buffer.size() >= BUFFER_SIZE)
			{
				try { System.out.println("in produce"); wait();}
				catch (InterruptedException ie) {}
			}
			buffer.add(in);
			System.out.println("Produced: " + in);
			in++;
			count++;

			notify();
			}	
		}
	
		public synchronized void consume()
		{
			int item;
			
			while (buffer.size() == 0)
			{
				try { System.out.println("In consume"); wait(); }
				catch (InterruptedException ie) {}
			}
	
			item = buffer.removeFirst();

			System.out.println("Consumed: " + item);
			
			notify();

			//might have to change this to return an int. maybe	
		}	
	}	

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
	public static void main(String[] args)
	{
		BoundedBuffer buf = new BoundedBuffer();
		Thread producer = new Thread(new producerThread(buf));
		Thread consumer = new Thread(new consumerThread(buf));	

		producer.start();
		consumer.start();

		//producer.join();
		//consumer.join();
			
	}
}
