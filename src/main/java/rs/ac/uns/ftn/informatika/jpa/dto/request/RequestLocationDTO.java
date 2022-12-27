package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.model.Departure;
import rs.ac.uns.ftn.informatika.jpa.model.Destination;
import rs.ac.uns.ftn.informatika.jpa.model.Location;

public class RequestLocationDTO {

    private Departure departure;
    private Destination destination;

    public RequestLocationDTO() {
    }

    public RequestLocationDTO(Departure departure,  Destination destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public Location parseToLocation(){
        return new Location(new Departure(), new Destination());
    }
}
