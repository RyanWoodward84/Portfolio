package question1;

/**
 * This class inherits from the Card class and specializes it further into an ID card, adding an id number as
 * an instance variable
 * @author Ryan Woodward
 * @version 1.0
 *
 */
public class IDCard extends Card
{
	private int idNumber;

	/**
	 * Default constructor for IDcard
	 * @pre The default constructor for the super class Card must be working properly
	 * @post Will instantiate an IDCard object
	 */
	public IDCard()
	{
		super();
		idNumber = 0;
	}

	/**
	 * Will construct an IDCard and initialize the variables with the explicit parameters passed
	 * @param n This is the name of the member on the card, calls the super constructor to initialize that variable
	 * @param id This is the id number on the id card as an integer
	 */
	public IDCard(String n, int id)
	{
		super(n);
		idNumber = id;
	}

	/**
	 * This method will return the value of the idNumber variable
	 * @return Returns the value of the idNumber as an int
	 */
	public int getIdNumber()
	{
		return idNumber;
	}

	/**
	 * This method will determine whether two IDCards are equal
	 * @param test This will be the IDcard passed that will be compared against this current IDCard
	 * @return Will return a true value if the two id card states are equal, false if otherwise
	 * @pre It is assumed that a non null ID card will be passed as an argument
	 * @post Will return true or false depending on the states of the two id cards
	 */
	public boolean equals(IDCard test)
	{
		if(super.equals(test) && (this.idNumber == test.getIdNumber()))
		{
			return true;
		}
		return false;
	}
	/**
	 * This method will override the super class method. It will call the super class to display the initial format, and
	 * then add on it's own format structure.
	 * @pre The super class format method must be in working order
	 */
	public String format()
	{
		String temp = super.format();
		temp += "ID Number: " + idNumber + "\n";
		return temp;
	}

	/**
	 *This method overrides the toString method, it will first call the superclass toString method, and then add
	 *on the idNumber to complete the String
	 *@return Will return a string describing the state of the object
	 *@pre It is assumed that the super class method is in working order
	 */
	public String toString()
	{
		return "IDCard[name=" + super.getName() +"][number=" + idNumber + "]";
	}
}
