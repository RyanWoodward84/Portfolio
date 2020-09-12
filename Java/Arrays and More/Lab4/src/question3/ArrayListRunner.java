package question3;
import java.util.ArrayList;

/** Will demonstrate ArrayLists and the various operations that are able to be performed
 *
 * @author Ryan Woodward
 *
 */
public class ArrayListRunner
{
	/** The driver method to run the program demonstrating ArrayLists and their capabilities
	 *
	 * @param names	An ArrayList to demonstrate methods and abilities
	 * @param names2 An ArrayList to copy elements from names, and to modify as well
	 */
	public static void main(String[] args)
	{
		ArrayList<String> names = new ArrayList<String>();
		System.out.println(names);
		System.out.println("Expected: []");

		//Adding elements to the Array List
		names.add("Alice");
		names.add("Bob");
		names.add("Connie");
		names.add("David");
		names.add("Edward");
		names.add("Fran");
		names.add("Gomez");
		names.add("Harry");
		System.out.println(names);
		System.out.println("Expected: [Alice, Bob, Connie, David, Edward, Fran, Gomez, Harry]");

		//Size of ArrayList
		System.out.println("Size of ArrayList: " + names.size());
		System.out.println("Expected: 8");

		//Printing the last name in the ArrayList
		System.out.println("Last Name in list: " + names.get(names.size() - 1));
		System.out.println("Expected: Harry");

		//Changing an element in the ArrayList
		names.set(0, "Alice B. Toklas");
		System.out.println("New first element name: " + names.get(0));
		System.out.println("Expected: Alice B. Toklas");

		//Adding Doug after David in the list
		names.add(4, "Doug");
		System.out.println("New ArrayList after add: " + names);
		System.out.println("Expected: [Alice B. Toklas, Bob, Connie, David, Doug, Edward, Fran, Gomez, Harry]");

		//Printing each name in the ArrayList using an enhanced for loop
		System.out.println("Names Printed using enhanced for loop: ");
		for(String name : names)
		{
			System.out.println(name);
		}

		//Building a new ArrayList using the ArrayList constructor that accepts another
		//ArrayList as an argument

		ArrayList<String> names2 = new ArrayList<String>(names);
		System.out.println(names2);
		System.out.println("Expected: [Alice B. Toklas, Bob, Connie, David, Doug, Edward, Fran, Gomez, Harry]");

		//Removing a name from the first list but not the second
		names.remove(0);
		System.out.println("names List: " + names);
		System.out.println("names2 List: " + names2);
	}

}
