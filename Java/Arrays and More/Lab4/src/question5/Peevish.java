package question5;

/**
 * This program will exemplify Peevish's door problem. Where it will use nested loops
 * to open and close the doors as you run through the number of attempts.
 *
 * @author Ryan Woodward
 * @version 1.0
 *
 */
public class Peevish
{

	/**
	 *
	 * @param door	This will be the array storing the state of the doors
	 * @param NODOORS	This will be the number of doors
	 * @param	noPasses	This will be the number of passes
	 * @param	OPEN	This will indicate if a door is open
	 * @param	CLOSED 	This will indicate if a door is closed
	 */
	public static void main(String[] args)
	{
		boolean[] door;
		final int NODOORS = 101;
		int noPasses = 100;
		// We will not use door[0]
		final boolean OPEN = true;
		final boolean CLOSED = false;
		// Initialize the doors
		door = new boolean[NODOORS];
		for (int i = 0; i < NODOORS; i++)
		{
			door[i] = CLOSED;
		}
		//Printing the state of each door after initializing the array
		printDoors(door, NODOORS);

		//Going through the passes up to 100
		stateDoors(door, NODOORS, noPasses);


		for (int i = 1; i < NODOORS	; i++)
		{
			System.out.println("Door " + i +": " + door[i]);
		}
		for (int i = 1; i < NODOORS; i++)
		{
			if (door[i])
			{
				System.out.println("Door " + i + " is open.");
			} else
			{
				System.out.println("Door " + i + " is closed.");
			}
		}
	}

	/** Will print the state of the doors within an array
	 *
	 * @param door	This will be the array of doors
	 * @param NODOORS	This will be the number of doors in the array
	 */
	private static void printDoors(boolean[] door, final int NODOORS)
	{
		for (int i = 1; i < NODOORS	; i++)
		{
			System.out.println("Door " + i +": " + door[i]);
		}
	}

	/** Will change the state of the doors depending on the number of doors in
	 *  the array, and the number of passes
	 *
	 * @param door	This will be the array of doors
	 * @param NODOORS	This will be the number of doors in the array
	 * @param noPasses	This will be the number of passes through the doors
	 */
	private static void stateDoors(boolean[] door, final int NODOORS, int noPasses)
	{
		for (int i = 1; i < noPasses; i++)
		{
			for(int j = i; j < NODOORS; j+=i)
			{
				if (door[j])
				{
					door[j] = false;
				}
				else
				{
					door[j] = true;
				}
			}
		}
	}

}
