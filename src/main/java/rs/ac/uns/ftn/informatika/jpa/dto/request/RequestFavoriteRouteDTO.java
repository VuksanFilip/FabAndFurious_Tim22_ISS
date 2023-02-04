package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestFavoriteRouteDTO {

    @NotBlank(message = "Cant be empty")
    @Length(max = 50, message = "You have exceeded the allowed length (50)")
    private String favoriteName;

    @Size(min=1, message = "Cannot be empty")
    private List<RequestLocationDTO> locations;

    @Size(min=1, message = "Cannot be empty")
    private List<ResponsePassengerIdEmailDTO> passengers;

    @Pattern(regexp = "STANDARD|LUXURY|VAN", flags = Pattern.Flag.CASE_INSENSITIVE, message = "must be STANDARD/LUXURY/VAN")
    private String vehicleType;

    @NotNull
    private boolean babyTransport;

    @NotNull
    private boolean petTransport;
}
