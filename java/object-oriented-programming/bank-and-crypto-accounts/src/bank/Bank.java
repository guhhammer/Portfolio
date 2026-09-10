package bank;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Facade that owns customers and every kind of account, keyed by number, and links accounts to customers. */
public class Bank {

    private final Map<String, Customer> customers = new LinkedHashMap<>();
    private final Map<String, CheckingAccount> checking = new LinkedHashMap<>();
    private final Map<String, LimitedCheckingAccount> limited = new LinkedHashMap<>();
    private final Map<String, SavingsAccount> savings = new LinkedHashMap<>();
    private final Map<String, Loan> loans = new LinkedHashMap<>();
    private final Map<String, Cryptocurrency> crypto = new LinkedHashMap<>();
    private final Map<String, List<String>> accountsOfCustomer = new LinkedHashMap<>();

    // ---- customers
    public Customer createCustomer(String taxId, String firstName, String lastName, String address, String email, String phone) {
        Customer c = new Person(taxId, firstName, lastName, address, email, phone);
        customers.put(taxId, c);
        accountsOfCustomer.put(taxId, new ArrayList<>());
        return c;
    }

    public Customer getCustomer(String taxId) { return customers.get(taxId); }

    private void link(String taxId, String label, String number) {
        accountsOfCustomer.get(taxId).add(label + " " + number);
    }

    // ---- checking
    public CheckingAccount createCheckingAccount(String taxId, String number, String branch, double balance) {
        CheckingAccount a = new Account(number, branch, balance);
        checking.put(number, a);
        link(taxId, "checking", number);
        return a;
    }

    public CheckingAccount getCheckingAccount(String number) { return checking.get(number); }

    // ---- limited checking
    public LimitedCheckingAccount createLimitedAccount(String taxId, String number, String branch, double balance, double limit, double rate) {
        LimitedCheckingAccount a = new LimitedAccount(number, branch, balance, limit, rate);
        limited.put(number, a);
        link(taxId, "limited checking", number);
        return a;
    }

    public LimitedCheckingAccount getLimitedAccount(String number) { return limited.get(number); }

    // ---- savings
    public SavingsAccount createSavingsAccount(String taxId, String number, String branch, double balance, int day, int month, int year, double monthlyRate) {
        SavingsAccount a = new Savings(number, branch, balance, day, month, year, monthlyRate);
        savings.put(number, a);
        link(taxId, "savings", number);
        return a;
    }

    public SavingsAccount getSavingsAccount(String number) { return savings.get(number); }

    // ---- loans
    public Loan createLoan(String taxId, String number, String branch, double balance, String loanNumber, double principal,
                           int rate, String issueDate, String dueDate) {
        Loan l = new AccountLoan(number, branch, balance, loanNumber, principal, rate, issueDate, dueDate);
        loans.put(loanNumber, l);
        link(taxId, "loan", loanNumber);
        return l;
    }

    public Loan getLoan(String loanNumber) { return loans.get(loanNumber); }

    // ---- crypto
    public Bitcoin createBitcoinAccount(String taxId, String number, String branch, double coins, double price) {
        CryptoAccount a = new CryptoAccount("BTC", number, branch, coins, price);
        crypto.put(number, a);
        link(taxId, "bitcoin", number);
        return a;
    }

    public Ethereum createEthereumAccount(String taxId, String number, String branch, double coins, double price) {
        CryptoAccount a = new CryptoAccount("ETH", number, branch, coins, price);
        crypto.put(number, a);
        link(taxId, "ethereum", number);
        return a;
    }

    public Cryptocurrency getCryptoAccount(String number) { return crypto.get(number); }

    // ---- reports
    public void printAccounts(String taxId) {
        Customer c = customers.get(taxId);
        System.out.println("\nCustomer: " + c.getFirstName() + " " + c.getLastName());
        List<String> list = accountsOfCustomer.get(taxId);
        if (list.isEmpty()) { System.out.println("  no account linked to this customer"); return; }
        for (String entry : list) {
            String number = entry.substring(entry.lastIndexOf(' ') + 1);
            double balance;
            if (entry.startsWith("checking")) { balance = checking.get(number).getBalance(); }
            else if (entry.startsWith("limited")) { balance = limited.get(number).getBalance(); }
            else if (entry.startsWith("savings")) { balance = savings.get(number).getBalance(); }
            else if (entry.startsWith("loan")) { balance = loans.get(number).getAccountBalance(); }
            else { balance = crypto.get(number).getBalance(); }
            System.out.printf("  %-18s %-8s balance: %,.2f%n", entry.substring(0, entry.lastIndexOf(' ')), number, balance);
        }
    }
}
