package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Location departure;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Location destination;
    private double km;

    public Route() {
    }

    public Route(Location departure, Location destination) {
        this.departure = departure;
        this.destination = destination;
        this.km = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getDeparture() {
        return departure;
    }

    public void setDeparture(Location departure) {
        this.departure = departure;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public RequestLocationDTO parseToResponse() {
        return new RequestLocationDTO(this.getDeparture().parseToResponse(), this.getDestination().parseToResponse());
    }
}
