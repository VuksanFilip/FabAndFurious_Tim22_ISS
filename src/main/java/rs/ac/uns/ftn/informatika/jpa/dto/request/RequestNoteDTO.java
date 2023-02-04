package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import rs.ac.uns.ftn.informatika.jpa.model.Note;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestNoteDTO {

    @NotBlank(message = "Cant be empty")
    @Length(max = 500, message = "You have exceeded the allowed length (500)")
    private String message;

    public Note parseToNote(User user){
        return new Note(this.message, new Date(), user);
    }
}
