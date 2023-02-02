package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class FavoriteRoutes {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String favoriteName;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "favourite_route_route", joinColumns = @JoinColumn(name = "favourite_route_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"))
    private List<Route> routes;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "favourite_route_passenger", joinColumns = @JoinColumn(name = "favourite_route_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id"))
    private List<Passenger> passengers;

    @Enumerated(EnumType.STRING)
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public FavoriteRoutes(String favoriteName, List<Route> routes, List<Passenger> passengers, VehicleName vehicleType, boolean babyTransport, boolean petTransport) {
        this.favoriteName = favoriteName;
        this.routes = routes;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }


    public ResponseFavoriteRouteWithScheduledTimeDTO parseToResponseWithScheduledTime() {
        List<RequestLocationDTO> routes = new ArrayList<>();
        for(Route r : this.routes){
            routes.add(r.parseToResponse());
        }
        List<ResponsePassengerIdEmailDTO> allPassengers = new ArrayList<>();
        for(Passenger p : this.passengers){
            allPassengers.add(p.parseToResponseIdEmail());
        }
        return new ResponseFavoriteRouteWithScheduledTimeDTO(this.id, this.favoriteName, new Date(), routes, allPassengers, this.vehicleType, this.babyTransport, this.petTransport);
    }

    public ResponseFavoriteRouteDTO parseToResponse() {
        List<RequestLocationDTO> routes = new ArrayList<>();
        for(Route r : this.routes){
            routes.add(r.parseToResponse());
        }
        List<ResponsePassengerIdEmailDTO> allPassengers = new ArrayList<>();
        for(Passenger p : this.passengers){
            allPassengers.add(p.parseToResponseIdEmail());
        }
        return new ResponseFavoriteRouteDTO(this.id, this.favoriteName, routes, allPassengers, this.vehicleType, this.babyTransport, this.petTransport);
    }

    public ResponseFavoriteRouteWithoutPassengersDTO parseToResponseWithoutPassengers() {
        List<RequestLocationDTO> routes = new ArrayList<>();
        for(Route r : this.routes){
            routes.add(r.parseToResponse());
        }
        return new ResponseFavoriteRouteWithoutPassengersDTO(this.id, this.favoriteName, routes, this.vehicleType, this.babyTransport, this.petTransport);

    }
}
