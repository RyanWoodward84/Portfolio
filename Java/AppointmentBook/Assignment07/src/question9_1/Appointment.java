package question9_1;
import question9_2.*;

/**
 * This class will describe an appointment with a description and a date
 * @author Ryan Woodward
 * @version 1.0
 *
 */
public class Appointment implements Appointable
{
	private String description;
	private String date;

	/**
	 * Default constructor for Appointment will set the instance variables to empty strings
	 */
	public Appointment()
	{
		description = "";
		date = "";
	}

	/**
	 * This constructor for Appointment will set the instance variables according the the explicit parameters
	 * @param decription This will set the description of the appointment as a String
	 * @param date This will set the date of the appointment as a String
	 * @pre It is assumed the date will be entered in a (YYYYMMDD) format
	 */
	public Appointment(String description, String date)
	{
		this.description = description;
		this.date = date;
	}

	/**
	 * This method will return the value of the instance variable description
	 * @return Returns the value of description as a String
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * This method will return the value of the instance variable date
	 * @return Returns the value of date as a String
	 */
	public String getDate()
	{
		return date;
	}

	public boolean occursOn(int year, int month, int day)
	{
		return true;
	}
}
