package rs.ac.uns.ftn.informatika.jpa.dto.create;

import rs.ac.uns.ftn.informatika.jpa.dto.response.PassengerIdEmailResponse;
import rs.ac.uns.ftn.informatika.jpa.dto.response.RideResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;

import java.util.ArrayList;

public class CreateRideDTO {

    private ArrayList<Location> locations;
    private ArrayList<Passenger> passengers;
    private Type vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public CreateRideDTO() {
    }

    public CreateRideDTO(ArrayList<Location> locations, ArrayList<Passenger> passengers, Type vehicleType, boolean babyTransport, boolean petTransport) {
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

    public Type getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Type type) {
        this.vehicleType = type;
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

    public RideResponseDTO parseToResponse(Long id){
        ArrayList<PassengerIdEmailResponse> passengerIdEmailResponses = new ArrayList<PassengerIdEmailResponse>();
        for(Passenger p : passengers){
            passengerIdEmailResponses.add(new PassengerIdEmailResponse(p.getId(), p.getEmail()));
        }

        ArrayList<Location> l = new ArrayList<Location>();
        Destination d = new Destination(1L, "1", 2.1, 2.2);
        Departure de = new Departure(1L, "1", 2.1, 2.2);
        l.add(d);
        l.add(de);

        RideResponseDTO rideResponse = new RideResponseDTO(id, passengerIdEmailResponses, this.vehicleType, this.babyTransport, this.petTransport,  l);
        return rideResponse;
    }

    public Ride parseToRide(Long id){
        return new Ride(id, this.locations, this.passengers, new VehicleType(this.vehicleType), this.babyTransport, this.petTransport);
    }
}
