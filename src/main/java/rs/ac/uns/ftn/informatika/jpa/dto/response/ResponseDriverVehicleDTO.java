package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Type;

public class ResponseDriverVehicleDTO {

    private Long id;
    private Long driverId;
    private Type type;
    private String model;
    private String licenseNumber;
    private ResponseLocationDTO location;
    private int passengerSeats;
    private boolean babyTransport;
    private boolean petTransport;

    public ResponseDriverVehicleDTO(Long id, Long driverId, Type type, String model, String licenseNumber, ResponseLocationDTO location, int passengerSeats, boolean babyTransport, boolean petTransport) {
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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

    public ResponseLocationDTO getLocation() {
        return location;
    }

    public void setLocation(ResponseLocationDTO location) {
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
