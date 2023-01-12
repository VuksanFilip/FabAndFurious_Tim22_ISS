package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDepartureDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDestinationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverVehicleDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLocationDTO;

import javax.persistence.*;
import java.util.List;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @OneToOne
    Driver driver;
    String vehicleModel;

    @OneToOne(cascade = {CascadeType.ALL})
    VehicleType type;
    String registarskeTablice;
    int seats;

    @OneToOne(cascade = {CascadeType.ALL})
    Location location;

    @OneToOne(cascade = {CascadeType.ALL})
    CurrentLocation currentLocation;

    boolean babyFriendly;
    boolean petFriendly;

    @OneToMany
    List<Review> reviews;

    public Vehicle() {
    }

    public Vehicle(Long id) {
        this.id = id;
    }

    public Vehicle(VehicleType vehicleType) {
        this.type = vehicleType;
    }

    public Vehicle(Long id, Long driverId, VehicleType type, String vehicleModel, String registarskeTablice, Location location, int seats, boolean babyFriendly, boolean petFriendly) {
        this.id = id;
        this.driver = new Driver(driverId);
        this.vehicleModel = vehicleModel;
        this.type = type;
        this.registarskeTablice = registarskeTablice;
        this.seats = seats;
        this.location = location;
        this.babyFriendly = babyFriendly;
        this.petFriendly = petFriendly;
    }

    public Vehicle(Driver driver, VehicleType type, String vehicleModel, String registarskeTablice, Location location, int seats, boolean babyFriendly, boolean petFriendly) {
        this.driver = driver;
        this.vehicleModel = vehicleModel;
        this.type = type;
        this.registarskeTablice = registarskeTablice;
        this.seats = seats;
        this.location = location;
        this.babyFriendly = babyFriendly;
        this.petFriendly = petFriendly;
    }

    public Vehicle(Driver driver, VehicleType type, String vehicleModel, String registarskeTablice, CurrentLocation currentLocation, int seats, boolean babyFriendly, boolean petFriendly) {
        this.driver = driver;
        this.vehicleModel = vehicleModel;
        this.type = type;
        this.registarskeTablice = registarskeTablice;
        this.seats = seats;
        this.currentLocation = currentLocation;
        this.babyFriendly = babyFriendly;
        this.petFriendly = petFriendly;
    }

    public Vehicle(Long id, Driver driver, String vehicleModel, VehicleType type, String registarskeTablice, int seats, Location location, boolean babyFriendly, boolean petFriendly, List<Review> reviews) {
        this.id = id;
        this.driver = driver;
        this.vehicleModel = vehicleModel;
        this.type = type;
        this.registarskeTablice = registarskeTablice;
        this.seats = seats;
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType tip) {
        this.type = tip;
    }

    public String getRegistarskeTablice() {
        return registarskeTablice;
    }

    public void setRegistarskeTablice(String registarskeTablice) {
        this.registarskeTablice = registarskeTablice;
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

    public void updateLocation(CurrentLocation location){

        this.currentLocation = location;
    }

    public ResponseDriverVehicleDTO parseToResponse(){
        ResponseDepartureDTO responseDepartureDTO = this.location.getDeparture().parseToResponse();
        ResponseDestinationDTO responseDestinationDTO = this.location.getDestination().parseToResponse();

        ResponseLocationDTO locationResponseDTO=new ResponseLocationDTO(responseDepartureDTO, responseDestinationDTO);

        return new ResponseDriverVehicleDTO(this.getId(), this.getDriver().getId(), this.type.type, this.getVehicleModel(), this.getRegistarskeTablice(), locationResponseDTO, this.getSeats(), this.isBabyFriendly(), this.isPetFriendly());
    }

}
