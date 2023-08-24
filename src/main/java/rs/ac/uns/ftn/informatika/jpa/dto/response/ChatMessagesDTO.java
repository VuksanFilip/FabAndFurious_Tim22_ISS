package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Chat;

import java.util.Date;

public class ChatMessagesDTO {

    private Long senderId;
    private Long receiverId;
    private String message;
    private Date sendingTime;
    private Long rideId;

    public ChatMessagesDTO() {
    }

    public ChatMessagesDTO(Long senderId, Long receiverId, String message, Date sendingTime, Long rideId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sendingTime = sendingTime;
        this.rideId = rideId;
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

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

}
