package question9_1;
import question9_2.*;

/**
 * This class will describe an appointment that occurs daily. It obtains all instance variables from the
 * superclass Appointment
 * @author Ryan Woodward
 * @version 1.0
 *
 */
public class Daily extends Appointment implements Appointable
{
	/**
	 * This constructor will create a Daily appointment, using the super constructor to initialize the instance
	 * variables
	 * @param description The description of the appointment as a String
	 * @param date The date of the appointment as a String
	 */
	public Daily(String description, String date)
	{
		super(description, date);
	}

	/**
	 * This method will determine whether or not the appointment occurs on this day. It will always return true
	 * since it occurs everyday
	 * @param year The year of the appointment to be checked
	 * @param month The month of the appointment to be checked
	 * @param day The day of the appointment to be checked
	 * @return Always returns true since it's a daily appointment
	 */
	public boolean occursOn(int year, int month, int day)
	{
		return true;
	}

	/**
	 * This method overrides the toString obejct method to display readable information of the Daily object and
	 * it's state
	 */
	public String toString()
	{
		return "[" + getDescription() + "]";
	}
}
