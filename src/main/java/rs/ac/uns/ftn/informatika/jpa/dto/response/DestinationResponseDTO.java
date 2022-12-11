package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class DestinationResponseDTO extends LocationResponseDTO{

    public DestinationResponseDTO() {
    }

    public DestinationResponseDTO(String address, double latitude, double longitude) {
        super(address, latitude, longitude);
    }
}
