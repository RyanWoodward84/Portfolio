package question9_2;

/**
 * This interface will provide the method header such that the Appointment, OneTime, Monthly, and Daily classes
 * will be able to use the appropriate method with respect to the class when either one of the methods occurs
 * within a collection
 * @author Ryan Woodward
 * @version 1.0
 *
 */
public interface Appointable
{
	boolean occursOn(int year, int month, int day);
	String toString();
}
