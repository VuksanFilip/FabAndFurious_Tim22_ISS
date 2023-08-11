package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import java.util.Date;
import java.util.List;

public class ResponseRideNewDTO {


    private Long id;
    private Date startTime;
    private Date endTime;
    private double totalCost;
    private ResponseDriverIdEmailDTO driver;
    private List<ResponsePassengerIdEmailDTO> passengers;
    private int estimatedTimeInMinutes;
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private ResponseRejectionReasonTimeOfDetectionDTO rejection;
    private List<RequestLocationDTO> locations;
    private RideStatus status;
    private Date scheduledTime;

    public ResponseRideNewDTO() {
    }

    public ResponseRideNewDTO(Long id, Date startTime, Date endTime, double totalCost, ResponseDriverIdEmailDTO driver, List<ResponsePassengerIdEmailDTO> passengers, int estimatedTimeInMinutes, VehicleName vehicleType, boolean babyTransport, boolean petTransport, ResponseRejectionReasonTimeOfDetectionDTO rejection, List<RequestLocationDTO> locations, RideStatus status, Date scheduledTime) {
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
        this.status = status;
        this.scheduledTime = scheduledTime;
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

    public ResponseDriverIdEmailDTO getDriver() {
        return driver;
    }

    public void setDriver(ResponseDriverIdEmailDTO driver) {
        this.driver = driver;
    }

    public List<ResponsePassengerIdEmailDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<ResponsePassengerIdEmailDTO> passengers) {
        this.passengers = passengers;
    }

    public List<RequestLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RequestLocationDTO> locations) {
        this.locations = locations;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public int getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public VehicleName getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleName vehicleType) {
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

    public ResponseRejectionReasonTimeOfDetectionDTO getRejection() {
        return rejection;
    }

    public void setRejection(ResponseRejectionReasonTimeOfDetectionDTO rejection) {
        this.rejection = rejection;
    }
}
