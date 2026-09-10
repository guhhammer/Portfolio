package bank;

/** A checking account with a per-withdrawal limit and an interest rate paid on deposits. */
public interface LimitedCheckingAccount extends CheckingAccount {

    double getLimit();

    double getInterestRate();

    void setInterestRate(double rate);

    /** Multiplies the limit by the factor and returns the new limit. */
    double raiseLimit(double factor);

    /** Reduces the limit by the fraction (0.1 = 10%) and returns the new limit. */
    double reduceLimit(double fraction);
}
