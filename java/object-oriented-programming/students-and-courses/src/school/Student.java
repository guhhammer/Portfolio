package school;

public class Student {

    private final String firstName, lastName;
    private final double grade1, grade2, grade3, grade4;

    public Student(String firstName, String lastName, double grade1, double grade2, double grade3, double grade4) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.grade1 = grade1;
        this.grade2 = grade2;
        this.grade3 = grade3;
        this.grade4 = grade4;
    }

    public double average() { return (grade1 + grade2 + grade3 + grade4) / 4; }

    public String report() {
        return "\nFirst name: " + firstName + "\nLast name: " + lastName
             + "\nGrade 1: " + grade1 + "\nGrade 2: " + grade2 + "\nGrade 3: " + grade3 + "\nGrade 4: " + grade4
             + "\nAverage: " + average() + "\n";
    }
}
