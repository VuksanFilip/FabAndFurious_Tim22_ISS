package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestAssumptionDTO {

    @Size(min=1, message = "Cannot be empty")
    private List<RequestLocationAssumptionDTO> locations;

    @NotBlank(message = "{required}")
    @Pattern(regexp = "STANDARD|LUXURY|VAN", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Must be STANDARD/LUXURY/VAN")
    private String vehicleType;

    @NotNull
    private boolean babyTransport;

    @NotNull
    private boolean petTransport;
}
