package rs.ac.uns.ftn.informatika.jpa.modell;

import java.util.Date;

enum MessageType{
    SUPPORT, RIDE, PANIC
}

public class Message {
    User sender;
    User reciever;
    String message;
    Date sendingtime;
    int driveID;

    public Message() {
    }

    public Message(User sender, User reciever, String message, Date sendingtime) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.sendingtime = sendingtime;
    }

    public Message(User sender, User reciever, String message, Date sendingtime, int driveID) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.sendingtime = sendingtime;
        this.driveID = driveID;
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
