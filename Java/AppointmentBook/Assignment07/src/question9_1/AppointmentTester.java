package question9_1;
import java.util.Scanner;

/**
 * This class will test out all of the features of oneTime, Daily, and Monthly appointments
 * @author Ryan Woodward
 * @version 1.0
 */
public class AppointmentTester
{
	/**
	 * This is the main driver method in order to test out the behaviour of the classes Appointment, OneTime,
	 * Daily, and Monthly. It will display all appointments that occur on a date passed by the user.
	 * @param args Command line arguments not used
	 */
	public static void main(String[] args)
	{
		final int NUM_OF_APPTS = 6;
		Scanner input = new Scanner(System.in);
		String userDate = "";
		int year = 0;
		int month = 0;
		int day = 0;
		boolean userCheck = true;

		//Setting up the Appointments array and creating the objects necessary
		Appointment[] appointments = new Appointment[NUM_OF_APPTS];

		OneTime ot1 = new OneTime("Dentist", "20140717");
		OneTime ot2 = new OneTime("Doctor", "20140703");
		Monthly monthly1 = new Monthly("Chiropractor", "20130917");
		Monthly monthly2 = new Monthly("Massage", "20140816");
		Daily daily1 = new Daily("School", "20140707");
		Daily daily2 = new Daily("Work", "20140717");

		appointments[0] = ot1;
		appointments[1] = ot2;
		appointments[2] = monthly1;
		appointments[3] = monthly2;
		appointments[4] = daily1;
		appointments[5] = daily2;

		//Asking user for date
		do
		{
			System.out.print("Please enter a date with the YYYYMMDD format: ");
			userDate = input.nextLine();

			//Checking to make sure each entry from the user is a digit
			for(int i = 0; i < userDate.length(); i++)
			{
				if(!Character.isDigit(userDate.charAt(i)))
				{
					userCheck = false;
				}
			}

			//Checking to make sure the length the user enter is 8
			if (userDate.length() != 8)
			{
				userCheck = false;
			}

			if (userCheck == false)
			{
				System.out.println("Invalid Input");
			}

			else
			{
				year = Integer.parseInt(userDate.substring(0,4));
				month = Integer.parseInt(userDate.substring(4,6));
				day = Integer.parseInt(userDate.substring(6));
			}
		} while (!userCheck);


		//Printing out the appointments for that day
		System.out.print("Appointments today: ");
		for (int i = 0; i < NUM_OF_APPTS; i++)
		{
			if(appointments[i].occursOn(year, month, day))
			{
				System.out.print(appointments[i]);
			}
		}
		System.out.println();
		input.close();

	}

}
