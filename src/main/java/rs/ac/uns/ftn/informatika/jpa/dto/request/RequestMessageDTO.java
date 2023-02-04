package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.enums.MessageType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMessageDTO {

    @NotBlank(message = "Cant be empty")
    @Length(max = 1000, message = "You have exceeded the allowed length (1000)")
    private String message;

    @Pattern(regexp = "SUPPORT|RIDE|PANIC", flags = Pattern.Flag.CASE_INSENSITIVE, message = "must be SUPPORT/RIDE/PANIC")
    private String type;

    private Long rideId;

    public Message parseToMessage(User sender, User reciever){
        return new Message(sender, reciever,  MessageType.valueOf(this.type), this.message, Date.from(Instant.now()), this.rideId);
    }
}
