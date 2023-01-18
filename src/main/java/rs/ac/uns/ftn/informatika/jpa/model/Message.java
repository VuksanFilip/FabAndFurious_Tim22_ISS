package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
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

    public Message(User sender, User reciever, MessageType type, String message, Date sendingTime) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.type = type;
        this.sendingTime = sendingTime;
    }

    public Message(User sender, User reciever, MessageType type, String message, Date sendingTime, int driveId) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.type = type;
        this.sendingTime = sendingTime;
        this.driveId = driveId;
    }

    public ResponseMessageDTO parseToDTO(){
        return new ResponseMessageDTO(this.id, this.sendingTime, this.sender.getId(), this.reciever.getId(), this.message, this.type, Long.valueOf(this.driveId));
    }
}
