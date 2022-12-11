package rs.ac.uns.ftn.informatika.jpa.dto.create;

import rs.ac.uns.ftn.informatika.jpa.dto.response.RideResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;

import java.util.ArrayList;

public class CreateRideDTO {

    private ArrayList<Location> locations;
    private ArrayList<Passenger> passengers;
    private VehicleType vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public CreateRideDTO() {
    }

    public CreateRideDTO(ArrayList<Location> locations, ArrayList<Passenger> passengers, VehicleType vehicleType, boolean babyTransport, boolean petTransport) {
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

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
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

    public Ride parseToRide(Long id){
        return new Ride(id, this.locations, this.passengers, this.vehicleType, this.babyTransport, this.petTransport);
    }
}
