package question4;

import java.util.ArrayList;
import java.util.Scanner;

/** Will declare an ArrayList of type String and has 5 customers. And also give
 *  have a separate array containing the balances of the customers
 *
 * @author Ryan Woodward
 * @version 1.0
 *
 */
public class CustomerLister {

	/**
	 * Will declare an ArrayList of type String and has 5 customers. And also give
	 *  have a separate array containing the balances of the customers
	 * @param customerBalance	An array containing the balances of the customers
	 * @param	customerNames	An ArrayList of Strings containing customer names
	 * @exception	It is assumed the user will enter doubles
	 */
	public static void main(String[] args)
	{
		final int CUSTOMERS = 5;
		Scanner in = new Scanner(System.in);

		double[] customerBalance = new double[CUSTOMERS];
		ArrayList<String> customerName = new ArrayList<String>(CUSTOMERS);
		customerName.add("Cathy");
		customerName.add("Ben");
		customerName.add("Jorge");
		customerName.add("Wanda");
		customerName.add("Freddie");

		for(String names : customerName)
		{
			System.out.println("Customer: " + names);
			System.out.print("Please enter a balance: ");
			customerBalance[0] = in.nextDouble();
			System.out.println();
		}

	}

}
