package question3;

/**
 * This class will represent a dog
 * @author Ryan Woodward
 * @version 1.0
 */
public class Cat implements Speakable
{
	private String name;

	/**
	 * This constructor will instantiate a cat object, setting the instance variable with the explicit parameter
	 * @param name This will set the instance variable name with the passed argumetn
	 * @pre It is assumed that a String will be passed
	 */
	public Cat(String name)
	{
		this.name = name;
	}

	/**
	 * This method will print out what a cat speaks
	 */
	public void speak()
	{
		System.out.println("Meow! Meow!");
	}

	/**
	 * This method will print what the class name is and the name of the cat
	 */
	public String toString()
	{
		return "Cat: " + name;
	}
}
