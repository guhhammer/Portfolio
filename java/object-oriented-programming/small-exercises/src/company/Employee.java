package company;

public class Employee {

    private final String firstName, lastName, birthDate;
    private final double hours;
    private final float hourlyRate;

    public Employee(String firstName, String lastName, String birthDate, int hours, float hourlyRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hours = hours;
        this.hourlyRate = hourlyRate;
    }

    public double salary() { return hours * hourlyRate; }

    public String payslip() {
        return "Name: " + firstName + " " + lastName + "\nBirth date: " + birthDate
             + "\nHours: " + hours + "\nHourly rate: " + hourlyRate + "\nSalary: " + salary() + "\n";
    }
}
