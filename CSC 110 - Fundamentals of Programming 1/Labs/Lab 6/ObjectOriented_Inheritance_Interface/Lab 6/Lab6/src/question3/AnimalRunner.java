package question3;
import java.util.*;

/**
 * This class will demonstate an interface between 2 objects that have similar methods
 * @author Ryan Woodward
 * @version 1.0
 *
 */
public class AnimalRunner
{

	/**
	 * This is the main driver method that will demonstrate that an interface can be used in order to exemplify
	 * that different object types with similar method bodies can implement an interface to use an Arraylist and
	 * an enhanced for loop in order to use either method simply.
	 * @param args No command arguments are used.
	 */
	public static void main(String[] args)
	{
		ArrayList<Speakable> dogcatList = new ArrayList<Speakable>();
		dogcatList.add(new Dog("Fred"));
		dogcatList.add(new Cat("Wanda"));
		for(Speakable obj : dogcatList)
		{
			obj.speak();
		}

	}

}
