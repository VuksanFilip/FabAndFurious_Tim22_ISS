package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Type;

public class ResponseDriverVehicleDTO {

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
    private Type type;
    private String model;
    private String licenseNumber;
    private ResponseDestinationLocationDTO location;
    private int passengerSeats;
    private boolean babyTransport;
    private boolean petTransport;

    public ResponseDriverVehicleDTO(Long id, Long driverId, Type type, String model, String licenseNumber, ResponseDestinationLocationDTO location, int passengerSeats, boolean babyTransport, boolean petTransport) {
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

    public ResponseDestinationLocationDTO getLocation() {
        return location;
    }

    public void setLocation(ResponseDestinationLocationDTO location) {
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
