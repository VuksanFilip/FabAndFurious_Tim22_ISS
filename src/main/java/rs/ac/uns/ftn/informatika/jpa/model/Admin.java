package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "admins")
public class Admin extends User{
}
