package school;

public class Teacher {

    private final String firstName, lastName;

    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }
}
