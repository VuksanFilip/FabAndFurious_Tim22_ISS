package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.VehicleType;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ResponsePanicRideDTO {
    private Long id;
    private Date startTime;
    private Date endTime;
    private double totalCost;
    private ResponsePanicRideDriverDTO driver;
    private ArrayList<ResponsePanicRidePassengerDTO> passengers;
    private int estimatedTimeInMinutes;
    private VehicleType vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private ResponsePanicRejectionDTO rejection;
    private ConcurrentHashMap<String, ResponsePanicRideLocationDTO> locations;

    public ResponsePanicRideDTO(Long id, Date startTime, Date endTime, double totalCost, ResponsePanicRideDriverDTO driver, ArrayList<ResponsePanicRidePassengerDTO> passengers, int estimatedTimeInMinutes, VehicleType vehicleType, boolean babyTransport, boolean petTransport, ResponsePanicRejectionDTO rejection, ConcurrentHashMap<String, ResponsePanicRideLocationDTO> locations) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.rejection = rejection;
        this.locations = locations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public ResponsePanicRideDriverDTO getDriver() {
        return driver;
    }

    public void setDriver(ResponsePanicRideDriverDTO driver) {
        this.driver = driver;
    }

    public ArrayList<ResponsePanicRidePassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<ResponsePanicRidePassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public int getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
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

    public ResponsePanicRejectionDTO getRejection() {
        return rejection;
    }

    public void setRejection(ResponsePanicRejectionDTO rejection) {
        this.rejection = rejection;
    }

    public ConcurrentHashMap<String, ResponsePanicRideLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(ConcurrentHashMap<String, ResponsePanicRideLocationDTO> locations) {
        this.locations = locations;
    }
}
