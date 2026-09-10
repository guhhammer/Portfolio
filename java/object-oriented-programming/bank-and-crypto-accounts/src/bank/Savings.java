package bank;

public class Savings extends Account implements SavingsAccount {

    private final int day, month, year;
    private final double monthlyRate;

    public Savings(String number, String branch, double balance, int day, int month, int year, double monthlyRate) {
        super(number, branch, balance);
        this.day = day;
        this.month = month;
        this.year = year;
        this.monthlyRate = monthlyRate;
    }

    @Override
    public int getDay() { return day; }

    @Override
    public int getMonth() { return month; }

    @Override
    public int getYear() { return year; }

    @Override
    public String getAnniversary() { return day + "/" + month + "/" + year; }

    /** Whole months between the anniversary and the given date (30-day months, 12-month years). */
    @Override
    public int monthsUntil(int d, int m, int y) {
        int days = (y - year) * 360 + (m - month) * 30 + (d - day);
        return Math.max(0, days / 30);
    }

    @Override
    public double accruedInterest(int d, int m, int y) {
        return getBalance() * monthlyRate * monthsUntil(d, m, y);
    }
}
