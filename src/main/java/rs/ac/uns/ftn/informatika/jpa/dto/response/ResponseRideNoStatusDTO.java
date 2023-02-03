package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

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
    private VehicleName vehicleVehicleName;
    private boolean babyTransport;
    private boolean petTransport;
    private ResponseRejectionReasonTimeOfDetectionDTO rejection;
    private ArrayList<ResponseLocationDTO> locations;

    public ResponseRideNoStatusDTO() {
    }

    public ResponseRideNoStatusDTO(Ride ride, ArrayList<ResponsePassengerIdEmailDTO> passengers, ArrayList<ResponseLocationDTO> locations, ResponseDriverIdEmailDTO driver, ResponseRejectionReasonTimeOfDetectionDTO rejection) {
        this.id = ride.getId();
        this.passengers = passengers;
        this.vehicleVehicleName = ride.getVehicle().getVehicleType().getVehicleName();
        this.babyTransport = ride.isBabyTransport();
        this.petTransport = ride.isPetTransport();
        this.locations = locations;
        this.startTime = ride.getStartTime();
        this.endTime = ride.getEndTime();
        this.totalCost = ride.getTotalCost();
        this.driver = driver;
        this.rejection = rejection;
        this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
    }

    public ResponseRideNoStatusDTO(Long id, Date startTime, Date endTime, int totalCost, ResponseDriverIdEmailDTO driver, ArrayList<ResponsePassengerIdEmailDTO> passengers, int estimatedTimeInMinutes, VehicleName vehicleVehicleName, boolean babyTransport, boolean petTransport, ResponseRejectionReasonTimeOfDetectionDTO rejection, ArrayList<ResponseLocationDTO> locations) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.vehicleVehicleName = vehicleVehicleName;
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

    public VehicleName getVehicleType() {
        return vehicleVehicleName;
    }

    public void setVehicleType(VehicleName vehicleVehicleName) {
        this.vehicleVehicleName = vehicleVehicleName;
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
