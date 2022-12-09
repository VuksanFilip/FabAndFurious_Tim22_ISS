package rs.ac.uns.ftn.informatika.jpa.model;

import java.util.Date;

enum MessageType{
    SUPPORT, RIDE, PANIC
}

public class Message {
    User sender;
    User reciever;
    String message;
    Date sendingtime;
    MessageType type;
    int driveID;

    public Message() {
    }

    public Message(User sender, User reciever, MessageType type, String message, Date sendingtime) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.type = type;
        this.sendingtime = sendingtime;
    }

    public Message(User sender, User reciever, MessageType type, String message, Date sendingtime, int driveID) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.type = type;
        this.sendingtime = sendingtime;
        this.driveID = driveID;
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

    public Date getSendingtime() {
        return sendingtime;
    }

    public void setSendingtime(Date sendingtime) {
        this.sendingtime = sendingtime;
    }

    public int getDriveID() {
        return driveID;
    }

    public void setDriveID(int driveID) {
        this.driveID = driveID;
    }
}
