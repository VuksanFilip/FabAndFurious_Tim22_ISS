package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestCurrentLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverVehicleDTO;

import javax.persistence.*;
import java.util.List;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.REFRESH}, mappedBy = "vehicle")
    private Driver driver;
    private String vehicleModel;

    @OneToOne(cascade = {CascadeType.ALL})
    private VehicleType vehicleType;
    private String licenseNumber;
    private int seats;

    @OneToOne(cascade = {CascadeType.ALL})
    private Location currentLocation;

    private boolean babyFriendly;
    private boolean petFriendly;

    @OneToMany
    private List<Review> reviews;

    public Vehicle() {
    }

    public Vehicle(Long id) {
        this.id = id;
    }

    public Vehicle(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Vehicle(Long id, Long driverId, VehicleType vehicleType, String vehicleModel, String licenseNumber, Location currentLocation, int seats, boolean babyFriendly, boolean petFriendly) {
        this.id = id;
        this.driver = new Driver(driverId);
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.licenseNumber = licenseNumber;
        this.seats = seats;
        this.currentLocation = currentLocation;
        this.babyFriendly = babyFriendly;
        this.petFriendly = petFriendly;
    }

    public Vehicle(Driver driver, VehicleType vehicleType, String vehicleModel, String licenseNumber, Location currentLocation, int seats, boolean babyFriendly, boolean petFriendly) {
        this.driver = driver;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.licenseNumber = licenseNumber;
        this.seats = seats;
        this.currentLocation = currentLocation;
        this.babyFriendly = babyFriendly;
        this.petFriendly = petFriendly;
    }

    public Vehicle(Driver driver, VehicleType vehicleType, String vehicleModel, String licenseNumber, int seats, boolean babyFriendly, boolean petFriendly) {
        this.driver = driver;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.licenseNumber = licenseNumber;
        this.seats = seats;
        this.babyFriendly = babyFriendly;
        this.petFriendly = petFriendly;
    }

    public Vehicle(Long id, Driver driver, String vehicleModel, VehicleType vehicleType, String licenseNumber, int seats, Location currentLocation, boolean babyFriendly, boolean petFriendly, List<Review> reviews) {
        this.id = id;
        this.driver = driver;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.licenseNumber = licenseNumber;
        this.seats = seats;
        this.currentLocation = currentLocation;
        this.babyFriendly = babyFriendly;
        this.petFriendly = petFriendly;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType tip) {
        this.vehicleType = tip;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isBabyFriendly() {
        return babyFriendly;
    }

    public void setBabyFriendly(boolean babyFriendly) {
        this.babyFriendly = babyFriendly;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    public void setPetFriendly(boolean petFriendly) {
        this.petFriendly = petFriendly;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }


    public ResponseDriverVehicleDTO parseToResponse(){
        return new ResponseDriverVehicleDTO(this.id, this.driver.getId(), this.vehicleType.getType(), this.vehicleModel, this.licenseNumber, this.currentLocation, this.seats, this.babyFriendly, this.petFriendly);
    }

    public void updateCurrentLocation(Location location) {
        this.getCurrentLocation().setAddress(location.getAddress());
        this.getCurrentLocation().setLatitude(location.getLatitude());
        this.getCurrentLocation().setLongitude(location.getLongitude());
    }
}
