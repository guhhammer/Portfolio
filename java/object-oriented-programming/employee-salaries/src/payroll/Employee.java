package payroll;

public class Employee {

    private final float hours, hourlyRate;
    private final String name;

    public Employee(int hours, int hourlyRate, String name) {
        this.hours = hours;
        this.hourlyRate = hourlyRate;
        this.name = name;
    }

    public String getName() { return name; }

    public float salary() { return hours * hourlyRate; }

    public float salary(int months) { return hours * hourlyRate * months; }
}
