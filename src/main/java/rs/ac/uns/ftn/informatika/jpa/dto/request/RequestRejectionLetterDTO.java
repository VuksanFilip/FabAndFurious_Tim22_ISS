package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import rs.ac.uns.ftn.informatika.jpa.model.RejectionLetter;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRejectionLetterDTO {

    @NotBlank(message = "Cant be empty")
    @Length(max = 1000, message = "You have exceeded the allowed length (1000)")
    String reason;

    public RejectionLetter parseToRejectionLetter(){
        return new RejectionLetter(this.reason);
    }
}
