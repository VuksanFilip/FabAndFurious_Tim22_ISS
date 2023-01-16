package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseFavoriteLocationsDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FavoriteRoute {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String favoriteName;

    @OneToOne(cascade = {CascadeType.ALL})
    private Route route;

    @ManyToMany
    @Column(name = "passenger_id")
    @JoinTable(name = "Passenger_FavoriteLocation",
            joinColumns = { @JoinColumn(name = "favoritepath_id") },
            inverseJoinColumns = { @JoinColumn(name = "passenger_id") }
    )
    private List<Passenger> passengers;

    @Enumerated
    private VehicleName vehicleVehicleName;
    private boolean babyTransport;
    private boolean petTransport;

    public FavoriteRoute() {

    }

    public FavoriteRoute(Long id, String favoriteName, Route route, List<Passenger> passengers, VehicleName vehicleVehicleName, boolean babyTransport, boolean petTransport) {
        this.id = id;
        this.favoriteName = favoriteName;
        this.route = route;
        this.passengers = passengers;
        this.vehicleVehicleName = vehicleVehicleName;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public FavoriteRoute(String favoriteName, Route route, List<Passenger> passengers, VehicleName vehicleVehicleName, boolean babyTransport, boolean petTransport) {
        this.favoriteName = favoriteName;
        this.route = route;
        this.passengers = passengers;
        this.vehicleVehicleName = vehicleVehicleName;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public VehicleName getVehicleType() {
        return vehicleVehicleName;
    }

    public void setVehicleType(VehicleName vehicleVehicleName) {
        this.vehicleVehicleName = vehicleVehicleName;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public void setBabyTransport(boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public boolean isPetTransport() {
        return petTransport;
    }

    public void setPetTransport(boolean petTransport) {
        this.petTransport = petTransport;
    }

    public ResponseFavoriteLocationsDTO parseToResponse(){

        ArrayList<ResponsePassengerIdEmailDTO> responsPassengerIdEmailDTOS = new ArrayList<>();
        for(Passenger p : passengers){
            responsPassengerIdEmailDTOS.add(new ResponsePassengerIdEmailDTO(p.getId(), p.getEmail()));
        }
        ArrayList<ResponseLocationDTO> responseLocationDTOS = new ArrayList<ResponseLocationDTO>();
//        for(Location l : route){
//            responseLocationDTOS.add(new ResponseLocationDTO(l.getDeparture().parseToResponse(), l.getDestination().parseToResponse()));
//        }
        return new ResponseFavoriteLocationsDTO(this.id, this.favoriteName, responseLocationDTOS, this.vehicleVehicleName, this.babyTransport, this.petTransport);
    }

    public List<ResponseFavoriteLocationsDTO> parseToResponseList(List<FavoriteRoute> favoriteLocations){
        List<ResponseFavoriteLocationsDTO> responseFavoriteLocations = new ArrayList<>();
        for(FavoriteRoute locations: favoriteLocations){
            responseFavoriteLocations.add(locations.parseToResponse());
        }
        return responseFavoriteLocations;
    }
}
