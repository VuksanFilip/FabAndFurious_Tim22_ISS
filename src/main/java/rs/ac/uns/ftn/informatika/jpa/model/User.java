package rs.ac.uns.ftn.informatika.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicUserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseUserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseUserWithIdDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.Provider;
import rs.ac.uns.ftn.informatika.jpa.model.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    private String firstName;

    private String lastName;

    private String picture;

    private String phoneNumber;

    private String email;

    private String address;

    private String password;

    private boolean blocked;

    private boolean active;

    private String resetPasswordToken;

    private LocalDateTime resetPasswordTokenExpiration;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.getRole().toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.blocked = blocked;
        this.active = active;
    }

    public User(String firstName, String lastName, String picture, String phoneNumber, String email, String address, boolean blocked, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.blocked = blocked;
        this.active = active;
    }

    public User(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.blocked = blocked;
        this.active = active;
    }

    public User(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
        this.blocked = false;
        this.active = false;
    }

    public User(String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public void setResetPasswordTokenExpiration(LocalDateTime resetPasswordTokenExpiration) {
        this.resetPasswordTokenExpiration = resetPasswordTokenExpiration;
    }

    public ResponseUserDTO parseToResponseUser(){
        return new ResponseUserDTO(this.firstName, this.lastName, this.picture, this.phoneNumber, this.email, this.address);
    }

    public ResponseUserWithIdDTO parseToResponseUserWithId(){
        return new ResponseUserWithIdDTO(this.id, this.firstName, this.lastName, this.picture, this.phoneNumber, this.email, this.address);
    }

    public ResponsePanicUserDTO parseToPanicResponse(){
        return new ResponsePanicUserDTO(this.firstName, this.lastName, this.picture, this.phoneNumber, this.email, this.address);
    }
}

