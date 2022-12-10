package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.ArrayList;

public class CreateRideDTO {

    private ArrayList<Location> locations;
    private ArrayList<Passenger> passengers;
    private String vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public CreateRideDTO() {
    }

    public CreateRideDTO(ArrayList<Location> locations, ArrayList<Passenger> passengers, String vehicleType, boolean babyTransport, boolean petTransport) {
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
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

    public RideResponseDTO parseToResponse(){
        RideResponseDTO rideResponse = new RideResponseDTO(this.passengers, this.vehicleType, this.babyTransport, this.petTransport, this.locations);
        return rideResponse;
    }
}
