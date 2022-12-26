package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.*;

import java.util.ArrayList;
import java.util.Date;

public class ResponseRideNoStatusDTO {

    private Long id;
    private Date startTime;
    private Date endTime;
    private int totalCost;
    private ResponseDriverIdEmailDTO driver;
    private ArrayList<ResponsePassengerIdEmailDTO> passengers;
    private int estimatedTimeInMinutes;
    private Type vehicleType;
    private boolean babyTransport;
    private boolean petFriendly;
    private ResponseRejectionReasonTimeOfDetectionDTO rejection;
    private ArrayList<ResponseLocationDTO> locations;

    public ResponseRideNoStatusDTO() {
    }

    public ResponseRideNoStatusDTO(Long id, ArrayList<ResponsePassengerIdEmailDTO> passengers, Type vehicleType, boolean babyTransport, boolean petFriendly, ArrayList<ResponseLocationDTO> locations) {
        this.id = id;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petFriendly = petFriendly;
        this.locations = locations;
        this.startTime = null;
        this.endTime = null;
        this.totalCost = 0;
        this.driver = new ResponseDriverIdEmailDTO(123L, null);
        this.rejection = new ResponseRejectionReasonTimeOfDetectionDTO();
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

    public ResponseDriverIdEmailDTO getDriver() {
        return driver;
    }

    public void setDriver(ResponseDriverIdEmailDTO driver) {
        this.driver = driver;
    }

    public ArrayList<ResponsePassengerIdEmailDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<ResponsePassengerIdEmailDTO> passengers) {
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

    public ArrayList<ResponseLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<ResponseLocationDTO> locations) {
        this.locations = locations;
    }

    public ResponseRejectionReasonTimeOfDetectionDTO getRejection() {
        return rejection;
    }

    public void setRejection(ResponseRejectionReasonTimeOfDetectionDTO rejection) {
        this.rejection = rejection;
    }
}
