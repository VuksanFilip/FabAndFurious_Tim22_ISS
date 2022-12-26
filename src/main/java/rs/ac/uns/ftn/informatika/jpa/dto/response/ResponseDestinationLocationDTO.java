package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class ResponseDestinationLocationDTO extends ResponseLocationDTO {

    public ResponseDestinationLocationDTO() {
    }

    public ResponseDestinationLocationDTO(String address, double latitude, double longitude) {
        super(address, latitude, longitude);
    }
}
