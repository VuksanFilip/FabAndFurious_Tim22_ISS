package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMessageWithIdDTO {

    private Long id;
    private Long sender;
    private Long receiver;

    @NotBlank(message = "Cant be empty")
    @Length(max = 1000, message = "You have exceeded the allowed length (1000)")
    private String message;
    private Date sentDateTime;

    @Pattern(regexp = "SUPPORT|RIDE|PANIC", flags = Pattern.Flag.CASE_INSENSITIVE, message = "must be SUPPORT/RIDE/PANIC")
    private String type;
    private Long rideId;
}
