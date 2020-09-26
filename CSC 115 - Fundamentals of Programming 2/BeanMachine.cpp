/*Ryan Woodward
 * 100201137
 * Assignment #3
 *
 * Description: This program will simulate a Galton box where balls are dropped
 * hit off pegs. The pegs will have a 50% chance of directing each ball left or
 * right. At the bottom of the box there are slots that will collect the number of
 * balls that have fallen into it.
 *
 *
 *
 * I pledge that I have completed the programming assignment independently.
 * I have not copied the code from a student or any other source.
 * I have not given my code to any student.
 * Ryan Woodward, May 17, 2014
 *
 * Input Layout: The user will be asked to enter an integer for the number of balls and the number
 *                      of slots. If anything other than an integer is entered than the program will crash
 *
 * Output Layout: The program will display the paths of each ball and will display a histogram as to where
 *                         each ball landed after they pass through the machine.
 *
 * Program Limitations / Bugs: If anything other than an integer is entered when asked
 *                                        for the number of balls or slots then the program will crash
 */

/*
 * Algorithm for Bean Machine
 *
 * First create an array for the number of slots and the path of the ball.
 * The max length of the arrays will be defined as 50.
 * Ask user how many balls and slots they want in that run of the simulation
 * 	Input user response into balls and slots
 * Use a while loop to run ball drop simulation for amount of balls that will be dropped according to user input
 * 	Initialize the slots array to 0 to allow increments if a ball falls inside that slot
 * 	Use for loop to loop through the array and set all ball slots[i] to 0
 *		Simulate a ball falling
 * 	Need random direction for each time a ball hits a peg
 * 	The amount of peg levels are = to the number of slots - 1
 * 	Set direction left or right when the ball hits that level of peg to L or R
 * 		Use rand % 2 to only allow 2 values (0 = L, 1 = R)
 * 	To Print out the path each ball loop through the ball path array and print the result
 * 		Use for (int i = 0; i < slots - 1; i++)
 * 	To Determine which slot to place the ball in, need to loop through the ball path array and count the number
 * 		of R's there are. Each R represents moving right in the ballSlots array.
 * 		Each time an R is read from ballPath increase a counter variable
 * 		Increment ballSlot[counter] by 1
 * 	To determine the height of the graph that will be needed we need to determine which slot has the most amount
 * 	of balls
 * 		Set a temp variable to keep track of the max number of balls
 * 		Run a for loop through the ballSlot[] array and if a slot has more balls then the previous slot then
 * 		set replace the tempHeight variable to that number
 * 	To print the graph
 * 		while the graphHeight counter is above 0 continue the loop
 *      on every iteration of the loop decrement the graphHeight variable and set the exit condition to graphHeight >= 0
 *      Next a for loop inside the while loop that will scan through the ballSlots array and determine if they have the amount of balls
 *      equal to graphHeight
 *          On each iteration of the for loop when i = 0 and graphHeight != set a "|" just for a border
 *          If ballSlots[i] = graphHeight then print out a "O" and decrement the ballSlots[i] by one
 *          If ballSlots[i] != graphHeight then print a " ".
 *          If the graphHeight = 0 then print out a "-" just to simulate the x-axis
 *
 */
#include <iostream>
#include <ctime>
#include <cstdlib>
using namespace std;


void pathGenerator(char ballPath[], int slots);
/*
 * Method: pathGenerator
 *
 * Purpose: This method accepts a character array, and an integer as the inputs, and uses random number generation to determine whether
 *              a ball will fall left or right off of a peg
 *
 * Preconditions: A character array must be passed along with an integer which represents the number of open slots at the bottom of
 *                  the bean machine
 *
 * Postconditions: Will fill the given array with either 'L' or 'R' to represent the path of a ball falling through the bean machine.
 *                  This method does not return anything
 */
void displayPath(char ballPath[], int slots);
/*
 * Method: displayPath
 *
 * Purpose: This method will display the path taken by a ball falling through the bean machine on the console.
 *
 * Preconditions: A character array must be passed along with an integer which represents the number of open slots at the bottom of
 *                  the bean machine
 *
 * Postconditions: Will display the contents of the character array to the console. This method doesn't return anything
 */
void slotPlacer(char ballPath[], int ballSlots[], int slots, int balls);
/*
 * Method: slotPlacer
 *
 * Purpose: This method will take the path of a ball, and place it in the appropriate slot of the bean machine
 *
 * Preconditions: A pre filled ballPath character array must be passed, along with a pre initialized ballSlots array. As well the number of slots
 *                  slots in the machine as an integer, and an integer representing the number of balls being dropped must be passed as well.
 *
 * Postconditions: Will count the number of 'R's in the ballPath character array and from that place the ball in the appropriate slot in the ballSlots
 *                  array. For example if there is one R in the ballPath array then the ballSlot[1] gets incremented by one. This method does not return
 *                  anything
 */
void slotInitializer(int ballSlots[], int slots);
/*
 * Method: slotInitializer
 *
 * Purpose: This method initializes the ballSlots integer array to 0 so that it can be incremented for later use
 *
 * Preconditions: an integer array ballSlots must be passed, as well as an integer representing the number of slots
 *
 * Postconditions: the passed integer array has all of it's elements get initialized to 0.
 */
int slotCounter(int ballSlots[], int slots);
/*
 * Method: slotCounter
 *
 * Purpose: This method counts all the balls in the ballSlots integer array in order to determine the height of the graph needed
 *
 * Preconditions: A pre initialized ballSlots integer array must be passed, along with an integer representing the number of slots
 *
 * Postconditions: Will return an integer representing a slot (or multiple slots) with the most number of balls in it.
 */
void graphGenerator(int graphHeight, int ballSlots[], int slots);
/*
 * Method: graphGenerator
 *
 * Purpose: This method will generate a graph representing where all the balls fell and display it to screen
 *
 * Preconditions: The height of the graph must be passed as an integer, a pre initialized ballSlots integer array, and an integer representing
 *                  the number of slots must be passed.
 *
 * Postconditions: Will print a graph to the screen representing the where all the balls fell.
 */

int main(){

	const int MAX_BALLS = 50;
	const int MAX_SLOTS = 50;
	srand(time(0)); //changes the seed of random

	int balls = 0;
	int slots = -1;
	char ballPath[MAX_SLOTS+1];
	int ballSlots[MAX_SLOTS+1];
	int graphHeight = 0;

	do
	{
		cout << "Please enter number of balls between 1 and 50: "; //User input loop asking for number of balls
		cin >> balls;
		cout << endl;
		if ((balls < 1) || (balls > MAX_BALLS))
			cout << "Invalid number of balls" << endl;
	} while ((balls < 1) || (balls > MAX_BALLS));

	do
	{
		cout << "Please enter number of slots: "; //User input loop asking for number of slots
		cin >> slots;
		if ((slots < 1) || (slots > MAX_SLOTS))
			cout << "Invalid number of slots" << endl;
	} while ((slots < 1) || (slots > MAX_SLOTS));

	slotInitializer(ballSlots, slots); //Initializing the ballSlots array elements to 0 so it can be incremented

	for (int i = 0; i < balls; i++) //The start of simulating dropping balls, this loop will run until number of balls is reached
	{
		pathGenerator(ballPath, slots); // This generates the path of the ball
		displayPath(ballPath, slots); // This will display the path of the ball on screen
		slotPlacer(ballPath, ballSlots, slots, balls); // This will place the ball in the appropriate slot
	}

	graphHeight = slotCounter(ballSlots, slots); // This will determine the graph height needed before hand to print. The graph then can be
                                                 // dynamic and the height can change with each iteration of the program
    
	graphGenerator(graphHeight, ballSlots, slots); //This will generate the graph and print it to screen


	return 0;
}

void pathGenerator(char ballPath[], int slots)
{
	int temp;

	if (slots == 1)
		ballPath[0] = 'L';
	for (int i = 0; i < (slots - 1); i++)
	{
		temp = rand() % 2;
		if (temp == 0)
			ballPath[i] = 'L';
		if (temp == 1)
			ballPath[i] = 'R';

	}
}

void displayPath(char ballPath[], int slots)
{

	if (slots == 1)
			cout << ballPath[0];
	for (int i = 0; i < slots - 1; i++)
	{
		cout << ballPath[i];
	}
	cout << endl;
}

void slotInitializer(int ballSlots[], int slots)
{
	for (int i = 0; i < slots ; i++)
	{
		ballSlots[i] = 0;
	}
}
void slotPlacer(char ballPath[], int ballSlots[], int slots, int balls)
{
	int counter = 0;
	if (slots == 1)
		ballSlots[0] = balls;
	else
	{
		for (int i = 0; i < slots - 1; i++)
		{
			if (ballPath[i] == 'R')
				counter++;
		}
		ballSlots[counter]++;
	}
}

int slotCounter(int ballSlots[], int slots)
{
	int tempHigh = 0;
	for (int i = 0; i < slots; i++)
	{
		if (ballSlots[i] > tempHigh)
			tempHigh = ballSlots[i];
	}
	cout << endl;

	return tempHigh;
}

void graphGenerator(int graphHeight, int ballSlots[], int slots)
{
	while (graphHeight >= 0)
	{
		for(int i = 0; i < slots; i++)
		{
			if ((i == 0) && (graphHeight != 0))
				cout << "|";

			if((ballSlots[i] == graphHeight) && graphHeight != 0)
			{
				cout << "O";
				ballSlots[i]--;
			}
			else if (graphHeight !=0)
			{
				cout << " ";
			}

			if ((i == slots - 1) && graphHeight != 0)
				cout << "|";

		}
		if (graphHeight == 0)
		{
			cout << "|";
			for(int i = 0; i < slots; i++)
			{
				cout << "-";
			}
			cout << "|";
		}

		graphHeight--;
		cout << endl;

	}
}
