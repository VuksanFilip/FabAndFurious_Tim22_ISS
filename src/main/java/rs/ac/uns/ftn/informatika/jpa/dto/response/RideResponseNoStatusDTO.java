package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.*;

import java.util.ArrayList;
import java.util.Date;

public class RideResponseNoStatusDTO {

    private Long id;
    private Date startTime;
    private Date endTime;
    private int totalCost;
    private DriverIdEmailResponse driver;
    private ArrayList<PassengerIdEmailResponse> passengers;
    private int estimatedTimeInMinutes;
    private Type vehicleType;
    private boolean babyTransport;
    private boolean petFriendly;
    private RejectionReasonTimeOfDetectionDTO rejection;
    private ArrayList<Location> locations;

    public RideResponseNoStatusDTO() {
    }

    public RideResponseNoStatusDTO(Long id, ArrayList<PassengerIdEmailResponse> passengers, Type vehicleType, boolean babyTransport, boolean petFriendly, ArrayList<Location> locations) {
        this.id = id;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petFriendly = petFriendly;
        this.locations = locations;
        this.startTime = null;
        this.endTime = null;
        this.totalCost = 0;
        this.driver = new DriverIdEmailResponse(123L, null);
        this.rejection = new RejectionReasonTimeOfDetectionDTO();
        this.estimatedTimeInMinutes = 0;
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

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public DriverIdEmailResponse getDriver() {
        return driver;
    }

    public void setDriver(DriverIdEmailResponse driver) {
        this.driver = driver;
    }

    public ArrayList<PassengerIdEmailResponse> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<PassengerIdEmailResponse> passengers) {
        this.passengers = passengers;
    }

    public int getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public Type getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Type vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public void setBabyTransport(boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public RejectionReasonTimeOfDetectionDTO getRejection() {
        return rejection;
    }

    public void setRejection(RejectionReasonTimeOfDetectionDTO rejection) {
        this.rejection = rejection;
    }
}
