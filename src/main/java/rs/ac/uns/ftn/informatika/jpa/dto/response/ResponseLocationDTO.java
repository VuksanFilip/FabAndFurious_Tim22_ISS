package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class ResponseLocationDTO {

    private ResponseDepartureDTO departure;
    private ResponseDestinationDTO destination;

    public ResponseLocationDTO() {
    }

    public ResponseLocationDTO(ResponseDepartureDTO departure, ResponseDestinationDTO destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public ResponseDepartureDTO getDeparture() {
        return departure;
    }

    public void setDeparture(ResponseDepartureDTO departure) {
        this.departure = departure;
    }

    public ResponseDestinationDTO getDestination() {
        return destination;
    }

    public void setDestination(ResponseDestinationDTO destination) {
        this.destination = destination;
    }
}
