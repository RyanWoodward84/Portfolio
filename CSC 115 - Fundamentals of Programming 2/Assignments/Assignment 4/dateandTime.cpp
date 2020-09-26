/*
 * Ryan Woodward
 * 100201137
 * CPSC 1160
 * Dr. Farrahi
 *
 * dateandTime.cpp
 *
 * Purpose: This program will invoke the time() function which displays the time elapsed in seconds since
 * 			midnight Jan 1, 1970. It will then display the current date and time by manipulating that output
 * 			into more readable results using c strings.
 *
 * Algorithm for date and time
 * ---------
 *
 * First need to invoke the time function and put it into an unsigned variable
 * 	unsigned currentTimeInSeconds = time(0);
 *
 * Need to create constant variable for month array to hold the name of the month.
 * 	const int MAX_MONTHS_CHARACTERS	 = 12;
 *
 * Need to create variables to hold day and year, unsigned since they can't be negative
 *
 * Call currDate method in order to change the variables to a readable format
 * 	From currDate call getYear method in order to change the year to the current one
 * 		year = 1970 + (seconds / (60 * 60 * 24 * 365.25))
 *
 *
 *
 */

#include <iostream>
#include <ctime>
#include <cstring>
using namespace std;

void currDate(unsigned seconds, char month[], unsigned & day, unsigned & year, unsigned & min, unsigned & sec);
void getYear(unsigned seconds, unsigned & year);
void getMonth(unsigned seconds, char month[], unsigned year);

int main()
{
	const int MAX_MONTHS_CHARACTERS = 9;

	unsigned currentTimeInSeconds = time(0);
	char month[MAX_MONTHS_CHARACTERS+1];
	unsigned day, year, min, sec;

	//Current Year Test
	cout << "Original Seconds: " << currentTimeInSeconds << endl;
	currDate(currentTimeInSeconds, month, day, year, min, sec);
	cout << "Year Test: " << year << endl;
	cout << "Month Test:" << month << endl;
}

/*
 * method: currDate
 *
 * Purpose: This method will take the initial time since Jan 1, 1970 and midnight in seconds variable and
 * 			manipulate that variable in order to compute the value of the current date and time in a more
 * 			readable format.
 *
 * Inputs: unsigned seconds : the time since Jan 1, 1970 at midnight in seconds
 * 			char month[] : the c-string that will store the current month as characters
 * 			unsigned& day : the unsigned day integer passed by reference
 * 			unsigned& min : the unsigned min integer passed by reference
 * 			unsigned& sec : the unsigned sec integer passed by reference
 *
 * Outputs: None, but beware that some of the input vales will be changed as they are passed by reference
 */
void currDate(unsigned seconds, char month[], unsigned & day, unsigned & year, unsigned & min, unsigned & sec)
{
	getYear(seconds, year);
	getMonth(seconds, month, year);
}

/*
 * method: getYear
 *
 * Purpose: This method will take the number of seconds and elapsed and will convert it into the current year. Note that
 * 			365.25 is used as the number of days in order to calculate a year in order to account for leap years
 *
 * Inputs: unsigned seconds: this is the numbers of seconds that have elapsed since Jan 1, 1970 at midnight
 * 			unsigned& year: this is the current year that is being passed by reference
 *
 * Outputs: Contains no outputs but will mutate the year variable as it is passed by reference
 */
void getYear(unsigned seconds, unsigned & year)
{
	year = 1970 + (seconds / (60*60*24*365.25)); //keep an eye on this formula as this will chop off the end
}

void getMonth(unsigned seconds, char month[], unsigned year)
{
	int baseYear < year;

	int januarySeconds = 31 * 60 * 60 * 24;
	int februarySeconds = 28 * 60 * 60 * 24;
	int marchSeconds = 31 * 60 * 60 * 24;
	int aprilSeconds = 30 * 60 * 60 * 24;
	int maySeconds = 31 * 60 * 60 * 24;
	int juneSeconds = 30 * 60 * 60 * 24;
	int julySeconds = 31 * 60 * 60 * 24;
	int augustSeconds = 31 * 60 * 60 * 24;
	int septemberSeconds = 30 * 60 * 60 * 24;
	int octoberSeconds = 31 * 60 * 60 * 24;
	int novemberSeconds = 30 * 60 * 60 * 24;

	do
	{


		baseYear++;
	} while (baseYear < year);

	/*	seconds = seconds / (60*60*24*365.25);
		cout << "Test for seconds: " << seconds << endl;
		cout << "Test for Jan Seconds: " << januarySeconds << endl;
		cout << "Test for Feb Seconds: " << februarySeconds << endl;

		if (januarySeconds / seconds == 1)
		{
			strcpy(month, "January");
			return;
		}
		else if ((januarySeconds + februarySeconds) / seconds  == 1)
		{
			strcpy(month, "February");
			return;
		}
		else if ((januarySeconds + februarySeconds + marchSeconds) / seconds == 1)
		{
			strcpy(month, "March");
			return;
		}
		else if ((januarySeconds + februarySeconds + marchSeconds + aprilSeconds) / seconds == 1)
		{
			strcpy(month, "April");
			return;
		}
		else if ((januarySeconds + februarySeconds + marchSeconds + aprilSeconds + maySeconds) / seconds == 1)
		{
			strcpy(month, "May");
			return;
		}
		else if ((januarySeconds + februarySeconds + marchSeconds + aprilSeconds + maySeconds + juneSeconds) / seconds == 1)
		{
			strcpy(month, "June");
			return;
		}
		else if ((januarySeconds + februarySeconds + marchSeconds + aprilSeconds + maySeconds + juneSeconds
					+ julySeconds) / seconds == 1)
		{
			strcpy(month, "July");
			return;
		}
		else if ((januarySeconds + februarySeconds + marchSeconds + aprilSeconds + maySeconds + juneSeconds
					+ julySeconds + augustSeconds) / seconds == 1)
		{
			strcpy(month, "August");
			return;
		}
		else if ((januarySeconds + februarySeconds + marchSeconds + aprilSeconds + maySeconds + juneSeconds
					+ julySeconds + augustSeconds + septemberSeconds) / seconds == 1)
		{
			strcpy(month, "September");
			return;
		}
		else if ((januarySeconds + februarySeconds + marchSeconds + aprilSeconds + maySeconds + juneSeconds
					+ julySeconds + augustSeconds + septemberSeconds + octoberSeconds) / seconds == 1)
		{
			strcpy(month, "October");
			return;
		}
		else if ((januarySeconds + februarySeconds + marchSeconds + aprilSeconds + maySeconds + juneSeconds
					+ julySeconds + augustSeconds + septemberSeconds + octoberSeconds + novemberSeconds) / seconds == 1)
		{
			strcpy(month, "November");
			return;
		}
		else
			strcpy(month, "December");
			*/
}

/*
 * method: isLeapYear
 *
 * Purpose: This method will determine whether or not the year variable passed is a leap year or not.
 *
 * Inputs:
 */
void isLeapYear(unsigned year, bool& leapYear)
{
	if (year % 4 == 0)
		leapYear = true;
	else
	{
		leapYear = false;
	}
}
