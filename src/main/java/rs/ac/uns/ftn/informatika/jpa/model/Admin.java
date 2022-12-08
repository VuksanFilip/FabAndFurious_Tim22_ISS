package rs.ac.uns.ftn.informatika.jpa.model;

public class Admin extends User{

    String username;

    public Admin() {
    }

    public Admin(String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
        super(firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
    }

    public Admin(String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active, String username) {
        super(firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
