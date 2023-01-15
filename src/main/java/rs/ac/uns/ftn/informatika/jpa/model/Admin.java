package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;

@Entity
public class Admin extends User{

    public Admin() {
    }

    public Admin(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, String username) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password);
    }
}
