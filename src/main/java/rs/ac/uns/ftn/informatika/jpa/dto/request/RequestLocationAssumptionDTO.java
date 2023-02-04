package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestLocationAssumptionDTO {

    private RequestLocationWithAddressDTO departure;
    private RequestLocationWithAddressDTO destination;

}
