package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationWithAddressDTO;

public class ResponseLocationDTO {

    private RequestLocationWithAddressDTO departure;
    private RequestLocationWithAddressDTO destination;

    public ResponseLocationDTO() {
    }

    public ResponseLocationDTO(RequestLocationWithAddressDTO departure, RequestLocationWithAddressDTO destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public RequestLocationWithAddressDTO getDeparture() {
        return departure;
    }

    public void setDeparture(RequestLocationWithAddressDTO departure) {
        this.departure = departure;
    }

    public RequestLocationWithAddressDTO getDestination() {
        return destination;
    }

    public void setDestination(RequestLocationWithAddressDTO destination) {
        this.destination = destination;
    }
}
