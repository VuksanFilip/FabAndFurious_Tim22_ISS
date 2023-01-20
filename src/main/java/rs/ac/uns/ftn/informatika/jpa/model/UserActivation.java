package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class UserActivation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;
    private LocalDateTime date;
    private int lifespan;

    public UserActivation(User user) {
        this.date = LocalDateTime.now();
        this.lifespan = 3;
        this.user = user;
    }

    public UserActivation( Long id, User user) {
        this.id = id;
        this.date = LocalDateTime.now();
        this.lifespan = 3;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getLifespan() {
        return lifespan;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public boolean checkIfExpired() {
        LocalDateTime expiryDate = date.plus(lifespan, ChronoUnit.MINUTES);
        if (expiryDate.isBefore(LocalDateTime.now())) return true;
        return false;
    }
}
