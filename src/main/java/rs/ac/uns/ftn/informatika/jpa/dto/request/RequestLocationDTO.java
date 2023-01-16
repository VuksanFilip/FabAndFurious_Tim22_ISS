package rs.ac.uns.ftn.informatika.jpa.dto.request;

public class RequestLocationDTO {

    private RequestLocationWithAddressDTO departure;
    private RequestLocationWithAddressDTO destination;

    public RequestLocationDTO() {
    }

    public RequestLocationDTO(RequestLocationWithAddressDTO departure,  RequestLocationWithAddressDTO destination) {
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
