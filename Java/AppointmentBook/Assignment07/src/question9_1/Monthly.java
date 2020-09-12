package question9_1;
import question9_2.*;

/**
 * This class describes an appointment that occurs monthly. It obtains all instance variables from the superclass
 * Appointment
 * @author Ryan Woodward
 * @version 1.0
 *
 */
public class Monthly extends Appointment implements Appointable
{
	/**
	 * This constructor will instantiate a Monthly object using the super constructor to initialize the
	 * instance variables
	 * @param description The description of the appointment as a String
	 * @param date The description of the appointment as a String
	 */
	public Monthly(String description, String date)
	{
		super(description, date);
	}

	/**
	 * This method will determine whether this monthly appointment occurs on the requested date
	 * @param year The year of the date to be checked
	 * @param month The month of the date to be checked
	 * @param day The day of the date to be checked
	 * @return Returns true if the appointment occurs on that day, false otherwise
	 */
	public boolean occursOn(int year, int month, int day)
	{
		String date = "";
		if(day < 10)
		{
			date += 0;
		}
		date += day;

		if (date.equals(getDate().substring(6)))
		{
			return true;
		}
		return false;
	}

	public String toString()
	{
		return "[" + getDescription() + "]";
	}
}
