package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRideDTO {

    @Size(min=1, message = "Cannot be empty")
    private List<RequestLocationDTO> locations;

    @Size(min=1, message = "Cannot be empty")
    private List<ResponsePassengerIdEmailDTO> passengers;

    @NotBlank(message = "{required}")
    @Pattern(regexp = "STANDARD|LUXURY|VAN", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Must be STANDARD/LUXURY/VAN")
    private String vehicleType;

    private boolean babyTransport;
    private boolean petTransport;
    private Date scheduledTime;
}
