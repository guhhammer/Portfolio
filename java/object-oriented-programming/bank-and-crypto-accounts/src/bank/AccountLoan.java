package bank;

/** A loan taken against an account. */
public class AccountLoan extends Account implements Loan {

    private final String loanNumber;
    private final double principal;
    private int interestRate;   // simple interest, as a multiplier of the principal
    private final String issueDate, dueDate;

    public AccountLoan(String number, String branch, double balance, String loanNumber, double principal,
                       int interestRate, String issueDate, String dueDate) {
        super(number, branch, balance);
        this.loanNumber = loanNumber;
        this.principal = principal;
        this.interestRate = interestRate;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    @Override
    public String getLoanNumber() { return loanNumber; }

    @Override
    public double getPrincipal() { return principal; }

    @Override
    public int getInterestRate() { return interestRate; }

    @Override
    public void setInterestRate(int rate) { this.interestRate = rate; }

    @Override
    public String getIssueDate() { return issueDate; }

    @Override
    public String getDueDate() { return dueDate; }

    @Override
    public double amountDue() { return principal * (1 + interestRate); }

    @Override
    public double settle() {
        withdraw(amountDue());
        return getBalance();
    }

    @Override
    public double getAccountBalance() { return getBalance(); }
}
