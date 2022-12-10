package rs.ac.uns.ftn.informatika.jpa.model;

import java.util.ArrayList;
import java.util.Date;

public class Ride {

    Long id;
    Date startTime;
    Date endTime;
    int totalCost;
    Driver driver;
    ArrayList<Passenger> passengers;
    ArrayList<Location> locations;
    ArrayList<Path> paths;
    int estimatedTimeInMinutes;
    ArrayList<Review> reviews;
    RejectionLetter letter;
    boolean panic;
    boolean babyTransport;
    boolean petFriendly;
    String vehicleType;
    String status;

    public Ride() {
    }

    public Ride(Date startTime, Date endTime, int totalCost, Driver driver, ArrayList<Passenger> passengers, ArrayList<Location> locations, ArrayList<Path> paths, int estimatedTimeInMinutes, ArrayList<Review> reviews, RejectionLetter letter, boolean panic, boolean babyTransport, boolean petFriendly, String vehicleType, String status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.locations = locations;
        this.paths = paths;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.reviews = reviews;
        this.letter = letter;
        this.panic = panic;
        this.babyTransport = babyTransport;
        this.petFriendly = petFriendly;
        this.vehicleType = vehicleType;
        this.status = status;
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

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public ArrayList<Path> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<Path> paths) {
        this.paths = paths;
    }

    public float getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public RejectionLetter getLetter() {
        return letter;
    }

    public void setLetter(RejectionLetter letter) {
        this.letter = letter;
    }

    public boolean isPanic() {
        return panic;
    }

    public void setPanic(boolean panic) {
        this.panic = panic;
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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
