package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.model.*;

import java.util.ArrayList;

public class RequestRideDTO {

    private ArrayList<Location> locations;
    private ArrayList<Passenger> passengers;
    private Type vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public RequestRideDTO() {
    }

    public RequestRideDTO(ArrayList<Location> locations, ArrayList<Passenger> passengers, Type vehicleType, boolean babyTransport, boolean petTransport) {
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

    public ResponseRideDTO parseToResponse(Long id){
        ArrayList<ResponsePassengerIdEmailDTO> responsPassengerIdEmailDTOS = new ArrayList<ResponsePassengerIdEmailDTO>();
        for(Passenger p : passengers){
            responsPassengerIdEmailDTOS.add(new ResponsePassengerIdEmailDTO(p.getId(), p.getEmail()));
        }

        ArrayList<ResponseLocationDTO> responseLocationDTOS = new ArrayList<ResponseLocationDTO>();
        ResponseDepartureLocationDTO responseDepartureDTO = new ResponseDepartureLocationDTO();
        ResponseDestinationLocationDTO responseDestinationDTO = new ResponseDestinationLocationDTO();
        responseLocationDTOS.add(responseDepartureDTO);
        responseLocationDTOS.add(responseDestinationDTO);

        ResponseRideDTO rideResponse = new ResponseRideDTO(id, responsPassengerIdEmailDTOS, this.vehicleType, this.babyTransport, this.petTransport, responseLocationDTOS);
        return rideResponse;
    }

    public Ride parseToRide(Long id){
        return new Ride(id, this.locations, this.passengers, new VehicleType(this.vehicleType), this.babyTransport, this.petTransport, new Driver(123L));
    }
}
