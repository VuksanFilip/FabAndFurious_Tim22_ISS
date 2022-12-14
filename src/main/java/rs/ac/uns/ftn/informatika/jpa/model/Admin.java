package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.Entity;

@Entity
public class Admin extends User{

    String username;

    public Admin() {
    }

    public Admin(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
    }

    public Admin(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active, String username) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
