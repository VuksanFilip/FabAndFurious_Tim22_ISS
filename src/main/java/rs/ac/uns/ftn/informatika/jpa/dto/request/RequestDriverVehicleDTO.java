package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

public class RequestDriverVehicleDTO {

//    {
//        "vehicleType": "STANDARD",
//            "model": "VW Golf 2",
//            "licenseNumber": "NS 123-AB",
//            "currentLocation": {
//        "address": "Bulevar oslobodjenja 46",
//                "latitude": 45.267136,
//                "longitude": 19.833549
//    },
//        "passengerSeats": 4,
//            "babyTransport": true,
//            "petTransport": true
//    }

    private VehicleName vehicleType;
    private String model;
    private String licenseNumber;
    private RequestLocationWithAddressDTO currentLocation;
    private int passengerSeats;
    private boolean babyTransport;
    private boolean petTransport;

    public VehicleName getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleName vehicleVehicleName) {
        this.vehicleType = vehicleVehicleName;
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

    public RequestLocationWithAddressDTO getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(RequestLocationWithAddressDTO currentLocation) {
        this.currentLocation = currentLocation;
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


    public Vehicle parseToVehicle(Driver driver, Location currentLocation){
        return new Vehicle(driver, new VehicleType(this.vehicleType), this.model, this.licenseNumber, currentLocation, this.passengerSeats, this.babyTransport, this.petTransport);
    }
}
