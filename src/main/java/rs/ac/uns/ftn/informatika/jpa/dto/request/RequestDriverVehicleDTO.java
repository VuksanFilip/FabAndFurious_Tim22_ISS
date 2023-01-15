package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverVehicleDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;

public class RequestDriverVehicleDTO {

    private Type vehicleType;
    private String model;
    private String licenseNumber;
    private CurrentLocation currentLocation;
    private int passengerSeats;
    private boolean babyTransport;
    private boolean petTransport;

    public Type getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Type vehicleType) {
        this.vehicleType = vehicleType;
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


    public Vehicle parseToVehicle(Driver driver){
        return new Vehicle(driver, new VehicleType(this.vehicleType), this.model, this.licenseNumber, this.currentLocation, this.passengerSeats, this.babyTransport, this.petTransport);
    }
}
