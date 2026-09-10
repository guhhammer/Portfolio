package bank;

/** Exercises every account type of the bank model with fictional data.
 *  Object-Oriented Programming course, PUCPR (2018): interfaces, inheritance and a facade class. */
public class Main {

    public static void main(String[] args) {
        Bank bank = new Bank();

        String taxId = "000.000.000-00";
        Customer customer = bank.createCustomer(taxId, "Ada", "Lovelace", "Curitiba, PR", "ada@example.com", "+55 41 90000-0000");
        System.out.println(customer.describe());

        CheckingAccount checking = bank.createCheckingAccount(taxId, "1234", "2345", 12);
        System.out.println("Checking balance: " + checking.getBalance());
        checking.deposit(2500);
        System.out.println("After deposit:    " + checking.getBalance());
        checking.withdraw(400);
        System.out.println("After withdrawal: " + checking.getBalance());

        LimitedCheckingAccount limited = bank.createLimitedAccount(taxId, "123456", "5412", 50000, 5000, 0.2);
        limited.deposit(12000);                      // positive balance: deposit earns 20%
        limited.withdraw(9000);                      // capped at the 5000 limit
        System.out.println("\nLimited account balance: " + limited.getBalance() + ", limit after raise: " + limited.raiseLimit(1.08));

        SavingsAccount savings = bank.createSavingsAccount(taxId, "903647", "3789", 1000000, 20, 10, 2015, 0.022);
        System.out.println("\nSavings opened " + savings.getAnniversary() + ", months until 25/12/2016: " + savings.monthsUntil(25, 12, 2016)
                + ", interest accrued: " + savings.accruedInterest(25, 12, 2016));

        Loan loan = bank.createLoan(taxId, "23456", "9304", 2500, "43287", 1000, 4, "10/12/2000", "11/05/2001");
        System.out.println("\nLoan " + loan.getLoanNumber() + " amount due: " + loan.amountDue() + ", balance after settling: " + loan.settle());

        Bitcoin btc = bank.createBitcoinAccount(taxId, "12785", "8374", 0.5, 60000);
        Ethereum eth = bank.createEthereumAccount(taxId, "22345", "4562", 12, 2050.5);
        System.out.println("\n" + btc.getSymbol() + " balance: " + btc.getBalance() + " coins = " + btc.balanceInCurrency(btc.getCoinPrice()));
        System.out.println(eth.getSymbol() + " balance: " + eth.getBalance() + " coins = " + eth.balanceInCurrency(eth.getCoinPrice()));

        customer.updateEmail("ada.lovelace@example.com");
        bank.printAccounts(taxId);
    }
}
