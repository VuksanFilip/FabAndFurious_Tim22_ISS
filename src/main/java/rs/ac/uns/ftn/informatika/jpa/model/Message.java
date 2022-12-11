package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.MessageResponseDTO;

import java.util.Date;

public class Message {
<<<<<<< Updated upstream
    User sender;
    User reciever;
    String message;
    Date sendingtime;
    int driveID;
=======
    private Long id;
    private User sender;
    private User reciever;
    private String message;
    private Date sendingtime;
    private MessageType type;
    private int driveID;
>>>>>>> Stashed changes

    public Message() {
    }

    public Message(User sender, User reciever, String message, Date sendingtime) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.sendingtime = sendingtime;
    }

<<<<<<< Updated upstream
    public Message(User sender, User reciever, String message, Date sendingtime, int driveID) {
=======
    public Message(Long id, User sender, User reciever, String message, Date sendingtime, MessageType type, int driveID) {
        this.id = id;
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.sendingtime = sendingtime;
        this.type = type;
        this.driveID = driveID;
    }

    public Message(User sender, User reciever, MessageType type, String message, Date sendingtime, int driveID) {
>>>>>>> Stashed changes
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.sendingtime = sendingtime;
        this.driveID = driveID;
    }

<<<<<<< Updated upstream
=======
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

>>>>>>> Stashed changes
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

    public MessageResponseDTO parseToDTO(){
        return new MessageResponseDTO(this.id, this.sendingtime, this.sender.getId(), this.reciever.getId(), this.message, this.type, Long.valueOf(this.driveID));
    }
}
