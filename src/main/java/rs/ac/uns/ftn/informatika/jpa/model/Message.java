package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ChatMessagesDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.MessageType;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private User reciever;

    private String message;
    private Date sendingTime;

    @Enumerated(EnumType.STRING)
    private MessageType type;
    private Long rideId;

    public Message(User sender, User reciever, MessageType type, String message, Date sendingTime) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.type = type;
        this.sendingTime = sendingTime;
    }

    public Message(User sender, User reciever, MessageType type, String message, Date sendingTime, Long rideId) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.type = type;
        this.sendingTime = sendingTime;
        this.rideId = rideId;
    }

    public ResponseMessageDTO parseToResponse(){
        return new ResponseMessageDTO(this.id, this.sendingTime, this.sender.getId(), this.reciever.getId(), this.message, this.type, this.rideId);
    }

    public ChatMessagesDTO parseToDTO(){
        return new ChatMessagesDTO(sender.getId(), reciever.getId(), message, sendingTime, rideId);
    }
}
