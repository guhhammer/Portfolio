package bank;

/** A bank customer identified by tax id. */
public interface Customer {

    String getTaxId();

    String getFirstName();

    String getLastName();

    String getAddress();

    String getEmail();

    String getPhone();

    void updatePhone(String phone);

    void updateAddress(String address);

    void updateEmail(String email);

    void updateLastName(String lastName);

    /** Human-readable record of the customer. */
    String describe();
}
