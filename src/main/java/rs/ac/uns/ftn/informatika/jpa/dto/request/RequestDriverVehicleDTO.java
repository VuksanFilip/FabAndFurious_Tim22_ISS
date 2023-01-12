package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverVehicleDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;

public class RequestDriverVehicleDTO {

    private Long id;
    private Type type;
    private String model;
    private String licenseNumber;
    private CurrentLocation currentLocation;
    private int passengerSeats;
    private boolean babyTransport;
    private boolean petTransport;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public CurrentLocation getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(CurrentLocation currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getPassengerSeats() {
        return passengerSeats;
    }

    public void setPassengerSeats(int passengerSeats) {
        this.passengerSeats = passengerSeats;
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

    public ResponseDriverVehicleDTO parseToResponse(Long id, Long driverId){
        ResponseLocationDTO responseDestinationDTO = new ResponseLocationDTO();
        ResponseDriverVehicleDTO driverVehicleResponse = new ResponseDriverVehicleDTO(id, driverId, this.type, this.model, this.licenseNumber, responseDestinationDTO, this.passengerSeats, this.babyTransport, this.petTransport);
        driverVehicleResponse.setId(id);
        return driverVehicleResponse;
    }

    public Vehicle parseToVehicle(Driver driver){
        return new Vehicle(driver, new VehicleType(this.type), this.model, this.licenseNumber, this.currentLocation, this.passengerSeats, this.babyTransport, this.petTransport);
    }
}
