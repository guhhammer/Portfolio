package bank;

/** A savings account that earns a monthly correction rate counted from its anniversary date. */
public interface SavingsAccount extends CheckingAccount {

    int getDay();

    int getMonth();

    int getYear();

    String getAnniversary();

    /** Whole months elapsed between the anniversary and the given date. */
    int monthsUntil(int day, int month, int year);

    /** Interest earned up to the given date. */
    double accruedInterest(int day, int month, int year);
}
