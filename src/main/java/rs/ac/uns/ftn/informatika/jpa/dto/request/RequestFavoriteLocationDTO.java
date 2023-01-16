package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import java.util.List;

public class RequestFavoriteLocationDTO {

    private String favoriteName;
    private Route route;
    private List<Passenger> passengers;
    private VehicleName vehicleVehicleName;
    private boolean babyTransport;
    private boolean petTransport;

    public RequestFavoriteLocationDTO(String favoriteName, Route route, List<Passenger> passengers, VehicleName vehicleVehicleName, boolean babyTransport, boolean petTransport) {
        this.favoriteName = favoriteName;
        this.route = route;
        this.passengers = passengers;
        this.vehicleVehicleName = vehicleVehicleName;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
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

    public VehicleName getVehicleVehicleName() {
        return vehicleVehicleName;
    }

    public void setVehicleVehicleName(VehicleName vehicleVehicleName) {
        this.vehicleVehicleName = vehicleVehicleName;
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

    public FavoriteRoute parseToFavoriteLocations(){
        return new FavoriteRoute(this.favoriteName, this.route, this.passengers, this.vehicleVehicleName, this.babyTransport, this.petTransport);
    }
}
