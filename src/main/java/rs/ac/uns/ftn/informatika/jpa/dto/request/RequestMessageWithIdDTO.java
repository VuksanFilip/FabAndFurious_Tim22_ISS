package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.Data;
import rs.ac.uns.ftn.informatika.jpa.model.enums.MessageType;

import java.sql.Date;

@Data
public class RequestMessageWithIdDTO {

    private Long id;
    private Long sender;
    private Long receiver;
    private String message;
    private Date sentDateTime;
    private MessageType type;
    private Long rideId;
}
