package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

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
    VehicleType vehicleType;
    RideStatus status;

    public Ride() {
    }

    public Ride(Driver driver, ArrayList<Passenger> passengers) {
        this.driver = driver;
        this.passengers = passengers;
    }

    public Ride(Long id, ArrayList<Location> locations, ArrayList<Passenger> passengers, VehicleType vehicleType, boolean babyTransport, boolean petFriendly) {
        this.id = id;
        this.passengers = passengers;
        this.locations = locations;
        this.babyTransport = babyTransport;
        this.petFriendly = petFriendly;
        this.vehicleType = vehicleType;
    }

    public Ride(Long id, ArrayList<Location> locations, ArrayList<Passenger> passengers, VehicleType vehicleType, boolean babyTransport, boolean petFriendly, Driver driver){
        this.id = id;
        this.passengers = passengers;
        this.locations = locations;
        this.babyTransport = babyTransport;
        this.petFriendly = petFriendly;
        this.vehicleType = vehicleType;
        this.driver = driver;
    }



    public Ride(Date startTime, Date endTime, int totalCost, Driver driver, ArrayList<Passenger> passengers, ArrayList<Location> locations, ArrayList<Path> paths, int estimatedTimeInMinutes, ArrayList<Review> reviews, RejectionLetter letter, boolean panic, boolean babyTransport, boolean petFriendly, VehicleType vehicleType, RideStatus status) {
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

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public void copyValues(Ride ride) {
        this.startTime = ride.getStartTime();
        this.endTime = ride.getEndTime();
        this.totalCost = (int) ride.getTotalCost();
        this.driver = ride.getDriver();
        this.passengers = ride.getPassengers();
        this.locations = ride.getLocations();
        this.paths = ride.getPaths();
        this.estimatedTimeInMinutes = (int) ride.getEstimatedTimeInMinutes();
        this.reviews = ride.getReviews();
        this.letter = ride.getLetter();
        this.panic = ride.isPanic();
        this.babyTransport = ride.isBabyTransport();
        this.petFriendly = ride.isPetFriendly();
        this.vehicleType = ride.getVehicleType();
        this.status = ride.getStatus();
    }

    public RideResponseDTO parseToResponse(){
        ArrayList<PassengerIdEmailResponse> passengerIdEmailResponses = new ArrayList<PassengerIdEmailResponse>();
        for(Passenger p : passengers){
            passengerIdEmailResponses.add(new PassengerIdEmailResponse(p.getId(), p.getEmail()));
        }

        ArrayList<LocationResponseDTO> locationResponseDTOS = new ArrayList<LocationResponseDTO>();
        for(Location l : locations){
            locationResponseDTOS.add(new LocationResponseDTO(l.getAddress(),l.getLatitude(), l.getLongitude()));
        }

        RideResponseDTO rideResponse = new RideResponseDTO(this.id, passengerIdEmailResponses, this.vehicleType.type, this.babyTransport, this.petFriendly, locationResponseDTOS);
        return rideResponse;
    }

    public RideResponseDTO parseToResponseWithStatus(){
        ArrayList<PassengerIdEmailResponse> passengerIdEmailResponses = new ArrayList<PassengerIdEmailResponse>();
        for(Passenger p : passengers){
            passengerIdEmailResponses.add(new PassengerIdEmailResponse(p.getId(), p.getEmail()));
        }

        ArrayList<LocationResponseDTO> locationResponseDTOS = new ArrayList<LocationResponseDTO>();
        for(Location l : locations){
            locationResponseDTOS.add(new LocationResponseDTO(l.getAddress(),l.getLatitude(), l.getLongitude()));
        }
        RideResponseDTO rideResponse = new RideResponseDTO(this.id, passengerIdEmailResponses, this.vehicleType.type, this.babyTransport, this.petFriendly, locationResponseDTOS, this.status);
        return rideResponse;
    }

    public RideResponseDTO parseToResponseWithStatusAndReason(){
        ArrayList<PassengerIdEmailResponse> passengerIdEmailResponses = new ArrayList<PassengerIdEmailResponse>();
        for(Passenger p : passengers){
            passengerIdEmailResponses.add(new PassengerIdEmailResponse(p.getId(), p.getEmail()));
        }

        ArrayList<LocationResponseDTO> locationResponseDTOS = new ArrayList<LocationResponseDTO>();
        for(Location l : locations){
            locationResponseDTOS.add(new LocationResponseDTO(l.getAddress(),l.getLatitude(), l.getLongitude()));
        }
        RideResponseDTO rideResponse = new RideResponseDTO(this.id, passengerIdEmailResponses, this.vehicleType.type, this.babyTransport, this.petFriendly, locationResponseDTOS, this.status, new RejectionReasonTimeOfDetectionDTO(this.letter.reason, this.letter.time));
        return rideResponse;
    }

    public RideResponseNoStatusDTO parseToResponseNoStatus(){
        ArrayList<PassengerIdEmailResponse> passengerIdEmailResponses = new ArrayList<PassengerIdEmailResponse>();
        for(Passenger p : passengers){
            passengerIdEmailResponses.add(new PassengerIdEmailResponse(p.getId(), p.getEmail()));
        }
        ArrayList<LocationResponseDTO> locationResponseDTOS = new ArrayList<LocationResponseDTO>();
        for(Location l : locations){
            locationResponseDTOS.add(new LocationResponseDTO(l.getAddress(),l.getLatitude(), l.getLongitude()));
        }
        RideResponseNoStatusDTO rideResponse = new RideResponseNoStatusDTO(this.id, passengerIdEmailResponses, this.vehicleType.type, this.babyTransport, this.petFriendly, locationResponseDTOS);
        return rideResponse;
    }

    public PanicRideResponseDTO parseToPanicResponse(){
        ConcurrentHashMap<String, PanicRideLocationResponseDTO> locations = new ConcurrentHashMap<>();
        locations.put("departure", new PanicRideLocationResponseDTO());
        locations.put("destionation", new PanicRideLocationResponseDTO());
        ArrayList<PanicRidePassengerResponseDTO> passengers = new ArrayList<>();
        for (Passenger p:this.passengers) {
            passengers.add(p.parseToPanicPassengersDTO());
        }
        return new PanicRideResponseDTO(this.id, this.startTime, this.endTime, this.totalCost, this.driver.parseToPanicDriverResponse(), passengers, this.estimatedTimeInMinutes, this.vehicleType, this.babyTransport, this.petFriendly, new PanicRejectionResponseDTO("reason1", new Date()), locations);
    }

}
