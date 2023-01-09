package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.model.*;

import java.util.List;

public class RequestFavoriteLocationDTO {

    private String favoriteName;
    private List<Location> locations;
    private List<Passenger> passengers;
    private Type vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public RequestFavoriteLocationDTO(String favoriteName, List<Location> locations, List<Passenger> passengers, Type vehicleType, boolean babyTransport, boolean petTransport) {
        this.favoriteName = favoriteName;
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
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

    public FavoriteLocations parseToFavoriteLocations(Long id){
        return new FavoriteLocations(id, this.favoriteName, this.locations, this.passengers, this.vehicleType, this.babyTransport, this.petTransport);
    }
}
