package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

public class RequestDriverVehicleDTO {

    private VehicleName vehicleVehicleName;
    private String model;
    private String licenseNumber;
    private Location currentLocation;
    private int passengerSeats;
    private boolean babyTransport;
    private boolean petTransport;

    public VehicleName getVehicleType() {
        return vehicleVehicleName;
    }

    public void setVehicleType(VehicleName vehicleVehicleName) {
        this.vehicleVehicleName = vehicleVehicleName;
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

//    public CurrentLocation getCurrentLocation() {
//        return currentLocation;
//    }
//
//    public void setCurrentLocation(CurrentLocation currentLocation) {
//        this.currentLocation = currentLocation;
//    }

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
        return new Vehicle(driver, new VehicleType(this.vehicleVehicleName), this.model, this.licenseNumber, this.currentLocation, this.passengerSeats, this.babyTransport, this.petTransport);
    }
}
