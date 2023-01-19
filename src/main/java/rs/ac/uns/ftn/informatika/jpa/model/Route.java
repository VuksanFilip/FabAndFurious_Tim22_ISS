package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
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

    public Route(Location departure, Location destination) {
        this.departure = departure;
        this.destination = destination;
        this.km = 0;
    }

    public RequestLocationDTO parseToResponse() {
        return new RequestLocationDTO(this.getDeparture().parseToResponse(), this.getDestination().parseToResponse());
    }
}
