package bank;

public class LimitedAccount extends Account implements LimitedCheckingAccount {

    private double limit;
    private double interestRate;

    public LimitedAccount(String number, String branch, double balance, double limit, double interestRate) {
        super(number, branch, balance);
        this.limit = limit;
        this.interestRate = interestRate;
    }

    @Override
    public double getLimit() { return limit; }

    @Override
    public double getInterestRate() { return interestRate; }

    @Override
    public void setInterestRate(double rate) { this.interestRate = rate; }

    @Override
    public double raiseLimit(double factor) { return limit = limit * factor; }

    @Override
    public double reduceLimit(double fraction) { return limit = limit * (1 - fraction); }

    /** Deposits into a positive account earn interest immediately. */
    @Override
    public void deposit(double amount) {
        super.deposit(getBalance() > 0 ? amount * (1 + interestRate) : amount);
    }

    /** A withdrawal is capped at the account limit. */
    @Override
    public void withdraw(double amount) { super.withdraw(Math.min(amount, limit)); }
}
