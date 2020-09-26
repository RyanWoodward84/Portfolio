package question1;

/**
 * This class provides the abstraction to represent a generic card
 * @author Ryan Woodward
 * @version 1.0
 * @param name - The name of the card holder
 *
 */
public class Card
{
	private String name;

	/**
	 * Default constructor to create a card will set the one instance variable name to an empty string
	 */
	public Card()
	{
		name = "";
	}

	/**
	 * Constructor to create a card will make the instance variable n equal to the explicit parameter n
	 * @param n
	 * @pre It is expected that a name is passed as a String
	 * @post A card is created with an initialized name
	 */
	public Card(String n)
	{
		name = n;
	}

	/**
	 * This method will return the current value of the instance variable name
	 * @return The value of the instance variable name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * If this method is called it to check whether the card is expired or not. Since there is no instance variable
	 * to determine this the return value of this method will determine whether the card is expired or not
	 * @return Returns a boolean value false to indicate the card isn't expired
	 */
	public boolean isExpired()
	{
		return false;
	}

	/**
	 * This method will determine whether two cards are equal or not
	 * @param test This is the test card to determine whether is equal or not
	 * @return Will return true if the two cards are equal, will return false if otherwise
	 * @pre It is assumed that the two cards will not be null when they are passed
	 * @post Will return a true or false value depending on whether the two cards are equal
	 */
	public boolean equals(Card test)
	{
		if(name.equals(test.getName()))
		{
			return true;
		}
		return false;


	}

	/**
	 * This method return a string with an easy to read format representing the name
	 * @return A string with an easy to read format for the name
	 */
	public String format()
	{
		return "Card holder: " + name + "\n";
	}

	/**
	 * This method overrides the toString method and prints out the name of the card holder
	 * @return Will return the name of the card holder
	 */
	public String toString()
	{
		return "Card[name=" + name + "]";
	}

}
