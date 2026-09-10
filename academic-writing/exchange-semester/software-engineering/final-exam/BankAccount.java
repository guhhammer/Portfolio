

public class BankAccount{


	private String accountNumber, accountHolder;
	private double balance; 
	private int accountType;


	BankAccount(){}

	BankAccount(String num, String holder, double balance, int type){

	}



	public String getAccountNumber(){ return this.accountNumber;}

	public String getAccountHolder(){ return this.accountHolder;}

	public String getAccountType(){ return this.accountType; }



	public String setAccountNumber(String n){ this.accountNumber = n;}

	public String setAccountHolder(String h){ this.accountHolder = h;}

	public String setAccountType(int i){ return this.accountType = i;} 



	public calculateTotalBalance(){}

}