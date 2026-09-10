package bank;

/** Base account: number, branch and a balance that can be deposited to and withdrawn from. */
public class Account implements CheckingAccount {

    private final String number;
    private final String branch;
    private double balance;

    public Account(String number, String branch, double balance) {
        this.number = number;
        this.branch = branch;
        this.balance = balance;
    }

    @Override
    public String getNumber() { return number; }

    @Override
    public String getBranch() { return branch; }

    @Override
    public double getBalance() { return balance; }

    protected void setBalance(double balance) { this.balance = balance; }

    @Override
    public void deposit(double amount) { balance += amount; }

    @Override
    public void withdraw(double amount) { balance -= amount; }
}
