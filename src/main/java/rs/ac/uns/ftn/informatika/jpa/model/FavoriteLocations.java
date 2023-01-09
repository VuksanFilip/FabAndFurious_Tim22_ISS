package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseFavoriteLocationsDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FavoriteLocations {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String favoriteName;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Location> locations;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Passenger> passengers;

    @Enumerated
    private Type vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public FavoriteLocations() {

    }

    public FavoriteLocations(String favoriteName, List<Location> locations, List<Passenger> passengers, Type vehicleType, boolean babyTransport, boolean petTransport) {
        this.favoriteName = favoriteName;
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
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

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Type getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Type vehicleType) {
        this.vehicleType = vehicleType;
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
        for(Location l : locations){
            responseLocationDTOS.add(new ResponseLocationDTO(l.getDeparture().parseToResponse(), l.getDestination().parseToResponse()));
        }
        return new ResponseFavoriteLocationsDTO(this.id, this.favoriteName, responseLocationDTOS, responsPassengerIdEmailDTOS, this.vehicleType, this.babyTransport, this.petTransport);
    }

    public List<ResponseFavoriteLocationsDTO> parseToResponseList(List<FavoriteLocations> favoriteLocations){
        List<ResponseFavoriteLocationsDTO> responseFavoriteLocations = new ArrayList<>();
        for(FavoriteLocations locations: favoriteLocations){
            responseFavoriteLocations.add(locations.parseToResponse());
        }
        return responseFavoriteLocations;
    }
}
