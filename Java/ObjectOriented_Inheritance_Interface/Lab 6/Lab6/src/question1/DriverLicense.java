package question1;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class defines a Driver's License. It extends the IDCard class and specializes it further by adding an
 * expiration year instance variable
 * @author Ryan
 *
 */
public class DriverLicense extends IDCard
{
	private int expYear;

	/**
	 * Default constructor for DriverLicense will initialize everything to 0
	 * @pre The super class IDCard must have a working default constructor
	 */
	public DriverLicense()
	{
		super();
		expYear = 0;
	}

	/**
	 * This will construct a DriverLicense initializing the expYear
	 * @param inputExp
	 * @pre The super class IDCard must have a working default constructor
	 */
	public DriverLicense(int inputExp)
	{
		super();
		expYear = inputExp;
	}

	/**
	 * This constructor will initialize all the instance variables according to the explicit parameter
	 * @param n Is used in a super constructor call to initialize the name on the card
	 * @param id Is used in a super constructor call to initialize the id number on the card
	 * @param inputExp Initializes the instance variable expYear
	 * @pre The super constructor must be in working order
	 */
	public DriverLicense(String n, int id, int inputExp)
	{
		super(n, id);
		expYear = inputExp;
	}

	/**
	 * This method will return the expiration year of the card
	 * @return The value of the instance variable expYear as an integer
	 */
	public int getExpYear()
	{
		return expYear;
	}

	/**
	 * This method will override the isExpired method of the Card superclass. It will compare the expiry year of
	 * the driver's license with the current year to determine whether it is false or not.
	 * @return Will return true if the card is expired, and false if it isn't.
	 */
	public boolean isExpired()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		if (expYear < year)
		{
			return true;
		}
		return false;
	}

	/**
	 * This method will determine whether the states of two DriverLicense objects are equal
	 * @param test This is the DriverLicense to be compared
	 * @return Will return a true if the states are equal, a false otherwise
	 * @pre It is assumed that a non-null DriverLicense will be passed
	 * @post Will return true if the states are equal, false otherwise
	 */
	public boolean equals(DriverLicense test)
	{
		if (super.equals(test) && (this.expYear == test.getExpYear()))
		{
			return true;
		}
		return false;
	}

	/**
	 * This method will override the super class ID card method. It will first call the super class method, and then
	 * add on it's own format structure as a String.
	 * @pre The super class format method must be in working order
	 */
	public String format()
	{
		String temp = super.format();
		temp += "Expiration Year: " + expYear + "\n";
		return temp;
	}

	/**
	 * This class will override the super class toString method. It will first call the super class toString method
	 * and then add on the expiration date
	 * @return Will return the state of the current object
	 * @pre It is assumed the the super class method toString is in working order
	 */
	public String toString()
	{
		return "DriverLicense[name=" + super.getName() + "][number=" + super.getIdNumber() + "][ExpYear=" + expYear +
				"]";
	}
}
