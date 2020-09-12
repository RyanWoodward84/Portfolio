package question3;

/**
 * This class will represent a dog, it has once instance variable to represent it's name
 * @author Ryan Woodward
 * @version 1.0
 *
 */
public class Dog implements Speakable
{
	private String name;

	/**
	 * This constructor will instantiate a Dog, setting the instance variable name with the passed argument
	 * @param name This explicit parameter will set the instance variable name
	 * @pre It is assumed that a String will be passed
	 */
	public Dog(String name)
	{
		this.name = name;
	}

	/**
	 * This method will print out what a dog speaks
	 */
	public void speak()
	{
		System.out.println("Woof! Woof!");
	}

	/**
	 * This method will print out the class type and the name of the dog
	 */
	public String toString()
	{
		return "Dog: " + name;
	}
}
