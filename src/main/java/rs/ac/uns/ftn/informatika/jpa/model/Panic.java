package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.PanicResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.PassengerResponseDTO;

import java.util.Date;

public class Panic {

    Long id;
    User user;
    Ride ride;
    Date time;
    String reason;

    public Panic() {
    }

    public Panic(Long id, User user, Ride ride, Date time, String reason) {
        this.id = id;
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.reason = reason;
    }

    public Panic(Long id, Date time, String reason, User user, Ride ride) {
        this.id = id;
        this.ride = ride;
        this.time = time;
        this.user = user;
        this.reason = reason;
    }

    public Panic(User user, Ride ride, Date time, String reason) {
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.reason = reason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public PanicResponseDTO parseToResponse(int numberOfPanics){
        return new PanicResponseDTO(numberOfPanics,this.id, this.time, this.reason, this.user, this.ride);
    }
}
