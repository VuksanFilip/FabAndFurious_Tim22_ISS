package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicSmallerDataDTO;

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

    public ResponsePanicSmallerDataDTO parseToResponseSmallerData(){
        return new ResponsePanicSmallerDataDTO(this.id, this.user.parseToResponseUser(), this.ride.parseToResponseNoStatus(), this.time, this.reason);
    }

    public ResponsePanicDTO parseToResponse(){
        return new ResponsePanicDTO(this.id, this.user.parseToPanicResponse(), this.ride.parseToPanicResponse(), this.time, this.reason);
    }
}
