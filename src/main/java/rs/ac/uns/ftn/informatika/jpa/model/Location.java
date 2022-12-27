package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDepartureDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDestinationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLocationDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToOne;

@Entity
@Inheritance
public class Location {

    @Id
    private Long id;

    @OneToOne
    private Departure departure;

    @OneToOne
    private Destination destination;

    public Location() {
    }

    public Location(Long id, Departure departure, Destination destination) {
        this.id = id;
        this.departure = departure;
        this.destination = destination;
    }

    public Location(Departure departure, Destination destination) {
        this.departure = departure;
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ResponseLocationDTO parseToResponse(){
        ResponseDepartureDTO departure = this.departure.parseToResponse();
        ResponseDestinationDTO destination = this.destination.parseToResponse();
        return new ResponseLocationDTO(departure, destination);
    }
}
