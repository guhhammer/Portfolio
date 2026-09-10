package bank;

/** An account whose balance is a number of coins of one cryptocurrency. */
public class CryptoAccount extends Account implements Bitcoin, Ethereum {

    private final String symbol;
    private final double coinPrice;

    public CryptoAccount(String symbol, String number, String branch, double coins, double coinPrice) {
        super(number, branch, coins);
        this.symbol = symbol;
        this.coinPrice = coinPrice;
    }

    @Override
    public String getSymbol() { return symbol; }

    @Override
    public double getCoinPrice() { return coinPrice; }

    @Override
    public double balanceInCurrency(double price) { return getBalance() * price; }
}
