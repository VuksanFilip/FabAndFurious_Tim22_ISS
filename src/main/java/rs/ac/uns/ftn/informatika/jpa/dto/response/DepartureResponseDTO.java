package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class DepartureResponseDTO extends LocationResponseDTO{

    public DepartureResponseDTO() {
    }

    public DepartureResponseDTO(String address, double latitude, double longitude) {
        super(address, latitude, longitude);
    }
}
