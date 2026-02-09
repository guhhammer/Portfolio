
import BankAccount;  // Import the BankAccount class.

public class BankAccountTest{

	private BankAccount bankAcc;

	@Before
	public void setUp(){
		bankAcc = new BankAccount();
	}


	@After
	public void tearDown(){

	}


	// Testing the BankAccount constructor with no Parameters.
	@Test
	public void testBankAccount_1(){

		BankAccount instance = bankAcc;

		String accNumberBefore = instance.getAccountNumber();

		instance = new BankAccount();

		String accNumberAfter = instance.getAccountNumber();

		// Was Null before:
		boolean check = (Null == accNumberBefore) && (accNumberBefore == accNumberAfter);

		// Was a String: 
		boolean check2 = (Null != accNumberBefore) && (accNumberAfter == Null);


		assertEquals((Null == accNumberBefore), (check || check2) && (instance instanceof BankAccount) );
		// Either it was null and was initialized as string or it was a string and was re-initialized as null.
		// And test if the instance is of type BankAccount
	}


	// Testing the BankAccount constructor with all Parameters.
	@Test
	public void testBankAccount_2(){

		BankAccount instance = bankAcc;

		instance = new BankAccount("Number", "Holder", 2.0, 1);

		boolean check = ((instance.getAccountNumber() == "Number") &&
						(instance.getAccountHolder() == "Holder") &&
						(instance.getBalance() == 2.0) &&
						(intance.getAccountType() == 1));


		assertEquals(true, check);
		// Check if variables were initialized properly.

	}



	@Test
	public void testCalculateTotalBalance(){


		int type = bankAcc.getAccountType();

		double totalBalance = 0.0;
		double currentBalance = 0.0; 

		boolean caught = false; int value = 0;
		if(type > 3 || type < 1){

			try{ value = bankAcc.calculateTotalBalance(); } catch(Exception e){ caught = true; e.getSuppressed(); }



		}
		else{

			// Total Balance = Balance + Balance * Interest Rate

			float interest = ((type == 1) ? 0.5 : (  (type == 2) ? 4.5 : 0.0 )) / 100;
			double balance = bankAcc.getBalance();

			double totalBalance = balance + (double)(balance * interest);
			double currentBalance = bankAcc.calculateTotalBalance();

		}

		assertEquals(true, (caught ||  (totalBalance == currentBalance && caught == false) ) );

		// Checks if balance was properly calculated or if the type input caused an error.

	}




}