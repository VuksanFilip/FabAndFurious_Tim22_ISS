package rs.ac.uns.ftn.informatika.jpa.model;


import rs.ac.uns.ftn.informatika.jpa.dto.HopInMessageDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class HopInMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timeOfSending;
    private Long senderId;
    private Long receiverId;
    private String message;
    private Long rideId;

    public HopInMessage() {}

    public HopInMessage(Long id, Long senderId, Long receiverId, LocalDateTime timeOfSending, String message, Long rideId) {
        super();
        this.id = id;
        this.timeOfSending = timeOfSending;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.rideId = rideId;
    }

    public HopInMessage(Long senderId, Long receiverId, HopInMessageDTO dto) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = dto.getMessage();
        this.rideId = dto.getRideId();
        this.timeOfSending = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeOfSending() {
        return timeOfSending;
    }

    public void setTimeOfSending(LocalDateTime timeOfSending) {
        this.timeOfSending = timeOfSending;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}