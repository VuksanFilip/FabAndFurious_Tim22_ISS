package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseUserDTO;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "admins")
public class Admin extends User{

    public ResponseUserDTO parseToResponse(){
        return new ResponseUserDTO(this.getFirstName(), this.getLastName(), this.getPicture(), this.getPhoneNumber(), this.getEmail(), this.getAddress());
    }
}
