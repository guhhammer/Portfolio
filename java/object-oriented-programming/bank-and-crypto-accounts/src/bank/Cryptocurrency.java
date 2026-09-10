package bank;

/** A checking account whose balance is held in a cryptocurrency. */
public interface Cryptocurrency extends CheckingAccount {

    String getSymbol();

    /** Price of one coin in the local currency. */
    double getCoinPrice();

    /** Balance converted at the given coin price. */
    double balanceInCurrency(double coinPrice);
}
