package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.VehicleType;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class PanicRideResponseDTO {
    private Long id;
    private Date startTime;
    private Date endTime;
    private double totalCost;
    private PanicRideDriverResponseDTO driver;
    private ArrayList<PanicRidePassengerResponseDTO> passengers;
    private int estimatedTimeInMinutes;
    private VehicleType vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private PanicRejectionResponseDTO rejection;
    private ConcurrentHashMap<String, PanicRideLocationResponseDTO> locations;

    public PanicRideResponseDTO(Long id, Date startTime, Date endTime, double totalCost, PanicRideDriverResponseDTO driver, ArrayList<PanicRidePassengerResponseDTO> passengers, int estimatedTimeInMinutes, VehicleType vehicleType, boolean babyTransport, boolean petTransport, PanicRejectionResponseDTO rejection, ConcurrentHashMap<String, PanicRideLocationResponseDTO> locations) {
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

    public PanicRideDriverResponseDTO getDriver() {
        return driver;
    }

    public void setDriver(PanicRideDriverResponseDTO driver) {
        this.driver = driver;
    }

    public ArrayList<PanicRidePassengerResponseDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<PanicRidePassengerResponseDTO> passengers) {
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

    public PanicRejectionResponseDTO getRejection() {
        return rejection;
    }

    public void setRejection(PanicRejectionResponseDTO rejection) {
        this.rejection = rejection;
    }

    public ConcurrentHashMap<String, PanicRideLocationResponseDTO> getLocations() {
        return locations;
    }

    public void setLocations(ConcurrentHashMap<String, PanicRideLocationResponseDTO> locations) {
        this.locations = locations;
    }
}
