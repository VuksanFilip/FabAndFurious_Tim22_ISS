package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Type;

import java.util.ArrayList;

public class ResponseFavoriteLocationsDTO {


    private Long id;
    private String favoriteName;
    private ArrayList<ResponseLocationDTO> locations;
    private ArrayList<ResponsePassengerIdEmailDTO> passengers;
    private Type vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public ResponseFavoriteLocationsDTO(Long id, String favoriteName, ArrayList<ResponseLocationDTO> locations, ArrayList<ResponsePassengerIdEmailDTO> passengers, Type vehicleType, boolean babyTransport, boolean petTransport) {
        this.id = id;
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

    public ArrayList<ResponseLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<ResponseLocationDTO> locations) {
        this.locations = locations;
    }

    public ArrayList<ResponsePassengerIdEmailDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<ResponsePassengerIdEmailDTO> passengers) {
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
}
