package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.ArrayList;

public class CreateUnregisteredUserDTO {

    private ArrayList<Location> locations;
    private ArrayList<Passenger> passenger;
    private String VehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public CreateUnregisteredUserDTO() {
    }

    public CreateUnregisteredUserDTO(ArrayList<Location> locations, ArrayList<Passenger> passenger, String vehicleType, boolean babyTransport, boolean petTransport) {
        this.locations = locations;
        this.passenger = passenger;
        VehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<Passenger> getPassenger() {
        return passenger;
    }

    public void setPassenger(ArrayList<Passenger> passenger) {
        this.passenger = passenger;
    }

    public String getVehicleType() {
        return VehicleType;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
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

    public UnregisteredUserReponseDTO parseToResponse(){
        UnregisteredUserReponseDTO unregisteredResponse = new UnregisteredUserReponseDTO();
        return unregisteredResponse;
    }
}
