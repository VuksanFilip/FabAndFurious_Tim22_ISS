package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.HopInMessage;

import java.time.LocalDateTime;

public class HopInMessageReturnedDTO {

    Long id;
    LocalDateTime timeOfSending;
    Long senderId;
    Long receiverId;
    String message;
    Long rideId;

    public HopInMessageReturnedDTO(Long id, Long senderId, Long receiverId, LocalDateTime timeOfSending, String message, Long rideId) {
        super();
        this.id = id;
        this.timeOfSending = timeOfSending;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.rideId = rideId;
    }

    public HopInMessageReturnedDTO(HopInMessage message) {
        super();
        this.id = message.getId();
        this.timeOfSending = message.getTimeOfSending();
        this.senderId = message.getSenderId();
        this.receiverId = message.getReceiverId();
        this.message = message.getMessage();
        this.rideId = message.getRideId();
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

    @Override
    public String toString() {
        return "MessageReturnedDTO [id=" + id + ", timeOfSending=" + timeOfSending + ", senderId=" + senderId
                + ", receiverId=" + receiverId + ", message=" + message + ", rideId=" + rideId + "]";
    }

}
