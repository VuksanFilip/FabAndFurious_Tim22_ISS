package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.enums.MessageType;

import java.sql.Date;
import java.time.Instant;

public class RequestMessageDTO {

    private String message;
    private MessageType type;
    private Long rideId;

    public RequestMessageDTO() {
    }

    public RequestMessageDTO(String message, MessageType type, Long rideId) {
        this.message = message;
        this.type = type;
        this.rideId = rideId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

//        private Long id;
//
//    @OneToOne
//    private User sender;
//
//    @OneToOne
//    private User reciever;
//    private String message;
//    private Date sendingTime;
//
//    @Enumerated(EnumType.STRING)
//    private MessageType type;
//    private int driveId;s

    public Message parseToMessage(User sender, User reciever){
        return new Message(sender, reciever,  this.type, this.message, Date.from(Instant.now()), this.rideId);
    }
}
