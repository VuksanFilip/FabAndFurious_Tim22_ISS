package rs.ac.uns.ftn.informatika.jpa.modell;

import java.util.Date;

public class RejectionLetter {

    Ride ride;
    String reason;
    User user;
    Date time;

    public RejectionLetter() {
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
