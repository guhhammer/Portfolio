package bank;

public interface CheckingAccount {

    String getNumber();

    String getBranch();

    double getBalance();

    void deposit(double amount);

    void withdraw(double amount);
}
