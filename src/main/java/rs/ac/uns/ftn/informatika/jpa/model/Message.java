package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.MessageType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message {

    @Id
    private Long id;

    @OneToOne
    private User sender;

    @OneToOne
    private User reciever;
    private String message;
    private Date sendingTime;

    @Enumerated(EnumType.STRING)
    private MessageType type;
    private int driveId;

    public Message() {
    }

    public Message(User sender, User reciever, MessageType type, String message, Date sendingTime) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.type = type;
        this.sendingTime = sendingTime;
    }

    public Message(Long id, User sender, User reciever, String message, Date sendingTime, MessageType type, int driveId) {
        this.id = id;
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.sendingTime = sendingTime;
        this.type = type;
        this.driveId = driveId;
    }

    public Message(User sender, User reciever, MessageType type, String message, Date sendingTime, int driveId) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.type = type;
        this.sendingTime = sendingTime;
        this.driveId = driveId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReciever() {
        return reciever;
    }

    public void setReciever(User reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    public int getDriveId() {
        return driveId;
    }

    public void setDriveId(int driveId) {
        this.driveId = driveId;
    }

    public ResponseMessageDTO parseToDTO(){
        return new ResponseMessageDTO(this.id, this.sendingTime, this.sender.getId(), this.reciever.getId(), this.message, this.type, Long.valueOf(this.driveId));
    }
}
