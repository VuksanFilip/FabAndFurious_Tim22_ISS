package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Path;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class PanicResponseDTO {

    private int totalCount;

    private Long id;
    private Date time;
    private String reason;
    private User user;
    private Ride ride;


    public PanicResponseDTO() {
    }

    public PanicResponseDTO(Long id, int totalCount, Date time, String reason) {
        this.id = id;
        this.totalCount = totalCount;
        this.time = time;
        this.reason = reason;
        this.ride=null;
        this.user=null;

    }

    public PanicResponseDTO(int numberOfPanics, Long id, Date time, String reason, User user, Ride ride) {
        this.totalCount = numberOfPanics;
        this.id = id;
        this.time = time;
        this.reason = reason;
        this.user = user;
        this.ride = ride;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
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
}
