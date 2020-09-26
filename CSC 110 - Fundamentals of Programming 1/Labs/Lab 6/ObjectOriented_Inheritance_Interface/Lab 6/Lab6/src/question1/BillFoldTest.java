package question1;

/**
 * This class contains a driver method that will test out the BillFold class
 * @author Ryan Woodward
 * @version 1.0
 *
 */
public class BillFoldTest {

	/**
	 * This is the main driver method that will test out the BillFold class.
	 * @param args Command line arguments are not used
	 */
	public static void main(String[] args)
	{
		//Testing out the first billfold
		BillFold billFold1 = new BillFold();

		//Test for two null cards
		System.out.println(billFold1.formatCards());
		System.out.println("Expected: Both cards are null");

		//Setting up 3 different cards
		DriverLicense driverLicense1 = new DriverLicense("Ryan Woodward", 100201137, 2013);
		CallingCard callingCard1 = new CallingCard("Call to Calgary", 5555555, 1234567);
		CallingCard callingCard2 = new CallingCard("Call to Oregaon", 4444444, 8901234);


		//Testing when one card has been added to billFold1 and one hasn't
		billFold1.addCard(driverLicense1);
		System.out.println(billFold1.formatCards());
		System.out.println("Expected: Card Holder: Ryan Woodward\nID Number: 100201137\nExpriationYear2014"
				+ "\n\n Card2 is not set");

		//Testing when both cards have been added to billFold
		billFold1.addCard(callingCard1);
		System.out.println(billFold1.formatCards());
		System.out.println("Expected: Card Holder: Ryan Woodward\nID Number: 100201137\nExpirationYear2014"
				+ "\n\nCard holder: Call to Calgary\nCard Number: 5555555\nPIN: 1234567");

		//Testing adding trying to add another card to billFold when 2 cards have already been added
		billFold1.addCard(callingCard2);
		System.out.println("Expected: No effects, both cards are set");

		//Testing the amount of expired cards
		System.out.println("The number of expired cards are " + billFold1.getExpiredCardCount());
		System.out.println("Expected: 1");
		System.out.println();

		//Testing out the toString methods
		System.out.println("Testing out toString methods");
		System.out.println(driverLicense1);
		System.out.println("Expected:DriverLicense[name=Ryan Woodward][number=100201137][ExpiryDate=2013]");
		System.out.println(callingCard1);
		System.out.println("Expected:CallingCard[name=Call to Calgary][number=5555555,pin=1234567]");

		//Testing out the equals methods of Card class
		Card cardTest1 = new Card("Ryan");
		Card cardTest2 = new Card("Ryan");
		Card cardTest3 = new Card("Not Ryan");

		System.out.println("cardTest1 and cardTest2 are equal: " + cardTest1.equals(cardTest2));
		System.out.println("Expected: true");
		System.out.println("cardTest1 and cardTest3 are equal: " + cardTest1.equals(cardTest3));
		System.out.println("Expected: false");

		//Testing out equals methods of ID card class
		IDCard idCardtest1 = new IDCard("Ryan", 1);
		IDCard idCardtest2 = new IDCard("Ryan", 1);
		IDCard idCardtest3 = new IDCard("Ryan", 2);

		System.out.println("idCardtest1 and idCardTest2 are equal: " + idCardtest1.equals(idCardtest2));
		System.out.println("Expected: true");
		System.out.println("idCardtest1 and idCardTest3 are equal: " + idCardtest1.equals(idCardtest3));
		System.out.println("Expected: false");

		//Testing out equals methods of CallingCard class
		CallingCard callingTest1 = new CallingCard("Ryan",1,1);
		CallingCard callingTest2 = new CallingCard("Ryan",1,1);
		CallingCard callingTest3 = new CallingCard("Ryan",1,2);

		System.out.println("callingTest1 and callingTest2 are equal: " + callingTest1.equals(callingTest2));
		System.out.println("Expected: true");
		System.out.println("callingTest1 and callingTest3 are equal: " + callingTest1.equals(callingTest3));
		System.out.println("Expected: false");

		//Testing out equals methods of DriverLicense class
		DriverLicense dlTest1 = new DriverLicense("Ryan", 1, 1);
		DriverLicense dlTest2 = new DriverLicense("Ryan", 1, 1);
		DriverLicense dlTest3 = new DriverLicense("Ryan", 1, 2);

		System.out.println("dlTest1 and dlTest2 are equal: " + dlTest1.equals(dlTest2));
		System.out.println("Expected: true");
		System.out.println("dlTest1 and dlTest3 are equal: " + dlTest1.equals(dlTest3));
		System.out.println("Expected: false");
	}

}
