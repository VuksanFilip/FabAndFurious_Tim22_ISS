package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.model.VehicleType;

public class DriverVehicleResponseDTO {

//    {
//        "id": 123,
//            "driverId": 123,
//            "vehicleType": "STANDARDNO",
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
    private Long id;
    private Long driverId;
    private VehicleType type;
    private String model;
    private String licenseNumber;
    private Location location;
    private int passengerSeats;
    private boolean babyTransport;
    private boolean petTransport;

    public DriverVehicleResponseDTO(Long id, Long driverId, VehicleType type, String model, String licenseNumber, Location location, int passengerSeats, boolean babyTransport, boolean petTransport) {
        this.id = id;
        this.driverId = driverId;
        this.type = type;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.location = location;
        this.passengerSeats = passengerSeats;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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


}
