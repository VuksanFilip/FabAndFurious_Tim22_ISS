package rs.ac.uns.ftn.informatika.jpa.dto.request;

public class RequestLocationDTO {

    private float departure;
    private float destination;

    public RequestLocationDTO() {
    }

    public RequestLocationDTO(float departure,  float destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public float getDeparture() {
        return departure;
    }

    public void setDeparture(float departure) {
        this.departure = departure;
    }

    public float getDestination() {
        return destination;
    }

    public void setDestination(float destination) {
        this.destination = destination;
    }

}
