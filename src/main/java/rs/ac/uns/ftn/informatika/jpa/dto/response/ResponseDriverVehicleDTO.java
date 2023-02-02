package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

public class ResponseDriverVehicleDTO {

    private Long id;
    private Long driverId;
    private VehicleName vehicleVehicleName;
    private String model;
    private String licenseNumber;
    private ResponseCurrentLocationDTO currentLocation;
    private int passengerSeats;
    private boolean babyTransport;
    private boolean petTransport;

    public ResponseDriverVehicleDTO(Long id, Long driverId, VehicleName vehicleVehicleName, String model, String licenseNumber, ResponseCurrentLocationDTO currentLocation, int passengerSeats, boolean babyTransport, boolean petTransport) {
        this.id = id;
        this.driverId = driverId;
        this.vehicleVehicleName = vehicleVehicleName;
        this.model = model;
        this.licenseNumber = licenseNumber;
        this.currentLocation = currentLocation;
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

    public ResponseCurrentLocationDTO getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(ResponseCurrentLocationDTO currentLocation) {
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


}
