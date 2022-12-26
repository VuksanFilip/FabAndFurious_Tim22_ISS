package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class ResponseDepartureLocationDTO extends ResponseLocationDTO {

    public ResponseDepartureLocationDTO() {
    }

    public ResponseDepartureLocationDTO(String address, double latitude, double longitude) {
        super(address, latitude, longitude);
    }
}
