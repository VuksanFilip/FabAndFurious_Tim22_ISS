package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RejectionLetter {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    Ride ride;
    String reason;

    @OneToOne
    User user;
    Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RejectionLetter() {
    }

    public RejectionLetter(String reason) {
        this.reason = reason;
    }

    public RejectionLetter(String reason, Date time) {
        this.reason = reason;
        this.time = time;

    }

    public RejectionLetter(Ride ride, String reason, User user, Date time) {
        this.ride = ride;
        this.reason = reason;
        this.user = user;
        this.time = time;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
