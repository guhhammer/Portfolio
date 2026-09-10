package bank;

/** A loan attached to an account: principal, simple interest rate and dates. */
public interface Loan {

    String getLoanNumber();

    double getPrincipal();

    int getInterestRate();

    void setInterestRate(int rate);

    String getIssueDate();

    String getDueDate();

    /** Principal plus interest. */
    double amountDue();

    /** Pays the loan off from the account balance and returns what is left. */
    double settle();

    double getAccountBalance();
}
