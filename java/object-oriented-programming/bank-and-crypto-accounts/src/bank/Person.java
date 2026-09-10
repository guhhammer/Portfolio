package bank;

public class Person implements Customer {

    private final String taxId, firstName;
    private String lastName, address, email, phone;

    public Person(String taxId, String firstName, String lastName, String address, String email, String phone) {
        this.taxId = taxId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String getTaxId() { return taxId; }

    @Override
    public String getFirstName() { return firstName; }

    @Override
    public String getLastName() { return lastName; }

    @Override
    public String getAddress() { return address; }

    @Override
    public String getEmail() { return email; }

    @Override
    public String getPhone() { return phone; }

    @Override
    public void updatePhone(String phone) { this.phone = phone; }

    @Override
    public void updateAddress(String address) { this.address = address; }

    @Override
    public void updateEmail(String email) { this.email = email; }

    @Override
    public void updateLastName(String lastName) { this.lastName = lastName; }

    @Override
    public String describe() {
        return "Tax id: " + taxId + "\nFirst name: " + firstName + "\nLast name: " + lastName
             + "\nAddress: " + address + "\nEmail: " + email + "\nPhone: " + phone + "\n";
    }
}
