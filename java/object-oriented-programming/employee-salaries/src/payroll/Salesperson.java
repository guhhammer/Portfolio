package payroll;

/** A salesperson earns a 3% commission. */
public class Salesperson extends Employee {

    private final float commission = 0.03f;

    public Salesperson(int hours, int hourlyRate, String name) { super(hours, hourlyRate, name); }

    @Override
    public float salary() { return super.salary() * (1 + commission); }

    @Override
    public float salary(int months) { return super.salary() * (1 + commission) * months; }
}
