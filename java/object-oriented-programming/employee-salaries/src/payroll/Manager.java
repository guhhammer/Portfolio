package payroll;

/** A manager earns a 2% performance bonus. */
public class Manager extends Employee {

    private final float performance = 0.02f;

    public Manager(int hours, int hourlyRate, String name) { super(hours, hourlyRate, name); }

    @Override
    public float salary() { return super.salary() * (1 + performance); }

    @Override
    public float salary(int months) { return super.salary() * (1 + performance) * months; }
}
