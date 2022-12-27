package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Entity
public class Ride {

    @Id
    private Long id;
    private Date startTime;
    private Date endTime;
    private int totalCost;

    @ManyToOne
    private Driver driver;

    @ManyToMany
    private List<Passenger> passengers;

    @OneToMany
    private List<Location> locations;

    @OneToMany
    private List<Path> paths;
    private int estimatedTimeInMinutes;

    @OneToMany
    private List<Review> reviews;

    @OneToOne
    private RejectionLetter letter;
    private boolean panic;
    private boolean babyTransport;
    private boolean petTransport;

    @OneToOne
    private VehicleType vehicleType;
    private RideStatus status;

    public Ride() {
    }

    public Ride(Driver driver, List<Passenger> passengers) {
        this.driver = driver;
        this.passengers = passengers;
    }

    public Ride(Long id, List<Location> locations, ArrayList<Passenger> passengers, VehicleType vehicleType, boolean babyTransport, boolean petTransport) {
        this.id = id;
        this.passengers = passengers;
        this.locations = locations;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.vehicleType = vehicleType;
    }

    public Ride(Long id, List<Location> locations, List<Passenger> passengers, VehicleType vehicleType, boolean babyTransport, boolean petTransport, Driver driver){
        this.id = id;
        this.passengers = passengers;
        this.locations = locations;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.vehicleType = vehicleType;
        this.driver = driver;
    }



    public Ride(Date startTime, Date endTime, int totalCost, Driver driver, List<Passenger> passengers, List<Location> locations, List<Path> paths, int estimatedTimeInMinutes, List<Review> reviews, RejectionLetter letter, boolean panic, boolean babyTransport, boolean petTransport, VehicleType vehicleType, RideStatus status) {
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
        this.petTransport = petTransport;
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

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

    public float getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
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

    public boolean isPetTransport() {
        return petTransport;
    }

    public void setPetTransport(boolean petTransport) {
        this.petTransport = petTransport;
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
        this.petTransport = ride.isPetTransport();
        this.vehicleType = ride.getVehicleType();
        this.status = ride.getStatus();
    }

    public void setDefaultData(){
        this.id = 123L;
        this.startTime = new Date();
        this.endTime = new Date();
        this.totalCost = 1235;
        this.driver = new Driver(123L, "user@example.com");
        this.passengers = new ArrayList<>(Arrays.asList(new Passenger(123L, "user@example.com")));
        this.locations = new ArrayList<>(Arrays.asList(new Location(new Departure(1L, "Bulevar oslobodjenja 46", 45.267136, 19.833549), new Destination(1L, "Bulevar oslobodjenja 46", 45.267136, 19.833549))));
        this.paths = new ArrayList<>(Arrays.asList(new Path()));
        this.estimatedTimeInMinutes = 5;
        this.reviews = new ArrayList<>(Arrays.asList(new Review()));
        this.letter = new RejectionLetter("Ride is canceled due to previous problems with the passenger");
        this.panic = true;
        this.babyTransport = true;
        this.petTransport = true;
        this.vehicleType = new VehicleType(Type.STANDARDNO);
        this.status = RideStatus.PENDING;
    }

    public ResponseRideDTO parseToResponse(){
        ArrayList<ResponsePassengerIdEmailDTO> responsPassengerIdEmailDTOS = new ArrayList<ResponsePassengerIdEmailDTO>();
        for(Passenger p : passengers){
            responsPassengerIdEmailDTOS.add(new ResponsePassengerIdEmailDTO(p.getId(), p.getEmail()));
        }

        ArrayList<ResponseLocationDTO> responseLocationDTOS = new ArrayList<ResponseLocationDTO>();
        for(Location l : locations){
            responseLocationDTOS.add(new ResponseLocationDTO(l.getDeparture().parseToResponse(), l.getDestination().parseToResponse()));
        }

        ResponseRideDTO rideResponse = new ResponseRideDTO(this.id, responsPassengerIdEmailDTOS, this.vehicleType.type, this.babyTransport, this.petTransport, responseLocationDTOS);

        return rideResponse;
    }

    public ResponseRideDTO parseToResponseDefault(){
        ArrayList<ResponsePassengerIdEmailDTO> responsPassengerIdEmailDTOS = new ArrayList<ResponsePassengerIdEmailDTO>();
        for(Passenger p : passengers){
            responsPassengerIdEmailDTOS.add(new ResponsePassengerIdEmailDTO(p.getId(), p.getEmail()));
        }

        ArrayList<ResponseLocationDTO> responseLocationDTOS = new ArrayList<ResponseLocationDTO>();
        for(Location l : locations){
            responseLocationDTOS.add(new ResponseLocationDTO(l.getDeparture().parseToResponse(), l.getDestination().parseToResponse()));
        }

        ResponseRideDTO rideResponse = new ResponseRideDTO(this.id, this.startTime, this.endTime, this.totalCost, new ResponseDriverIdEmailDTO(this.driver.getId(), this.driver.getEmail()), responsPassengerIdEmailDTOS, this.estimatedTimeInMinutes, this.getVehicleType().type, this.babyTransport, this.petTransport, new ResponseRejectionReasonTimeOfDetectionDTO(this.letter.getReason(), this.letter.getTime()), responseLocationDTOS, this.status);

        return rideResponse;
    }

    public ResponseRideDTO parseToResponseWithStatus(){
        ArrayList<ResponsePassengerIdEmailDTO> responsPassengerIdEmailDTOS = new ArrayList<ResponsePassengerIdEmailDTO>();
        for(Passenger p : passengers){
            responsPassengerIdEmailDTOS.add(new ResponsePassengerIdEmailDTO(p.getId(), p.getEmail()));
        }

        ArrayList<ResponseLocationDTO> responseLocationDTOS = new ArrayList<ResponseLocationDTO>();
        for(Location l : locations){
            responseLocationDTOS.add(new ResponseLocationDTO(l.getDeparture().parseToResponse(), l.getDestination().parseToResponse()));
        }
        ResponseRideDTO rideResponse = new ResponseRideDTO(this.id, responsPassengerIdEmailDTOS, this.vehicleType.type, this.babyTransport, this.petTransport, responseLocationDTOS, this.status);
        return rideResponse;
    }

    public ResponseRideDTO parseToResponseWithStatusAndReason(){
        ArrayList<ResponsePassengerIdEmailDTO> responsPassengerIdEmailDTOS = new ArrayList<ResponsePassengerIdEmailDTO>();
        for(Passenger p : passengers){
            responsPassengerIdEmailDTOS.add(new ResponsePassengerIdEmailDTO(p.getId(), p.getEmail()));
        }

        ArrayList<ResponseLocationDTO> responseLocationDTOS = new ArrayList<ResponseLocationDTO>();
        for(Location l : locations){
            responseLocationDTOS.add(new ResponseLocationDTO(l.getDeparture().parseToResponse(), l.getDestination().parseToResponse()));
        }
        ResponseRideDTO rideResponse = new ResponseRideDTO(this.id, responsPassengerIdEmailDTOS, this.vehicleType.type, this.babyTransport, this.petTransport, responseLocationDTOS, this.status, new ResponseRejectionReasonTimeOfDetectionDTO(this.letter.reason, this.letter.time));
        return rideResponse;
    }

    public ResponseRideNoStatusDTO parseToResponseNoStatus(){
        ArrayList<ResponsePassengerIdEmailDTO> responsPassengerIdEmailDTOS = new ArrayList<ResponsePassengerIdEmailDTO>();
        for(Passenger p : passengers){
            responsPassengerIdEmailDTOS.add(new ResponsePassengerIdEmailDTO(p.getId(), p.getEmail()));
        }
        ArrayList<ResponseLocationDTO> responseLocationDTOS = new ArrayList<ResponseLocationDTO>();
        for(Location l : locations){
            responseLocationDTOS.add(new ResponseLocationDTO(l.getDeparture().parseToResponse(), l.getDestination().parseToResponse()));
        }
        ResponseRideNoStatusDTO rideResponse = new ResponseRideNoStatusDTO(this.id, responsPassengerIdEmailDTOS, this.vehicleType.type, this.babyTransport, this.petTransport, responseLocationDTOS);
        return rideResponse;
    }

    public ResponsePanicRideDTO parseToPanicResponse(){
        ConcurrentHashMap<String, ResponsePanicRideLocationDTO> locations = new ConcurrentHashMap<>();
        locations.put("departure", new ResponsePanicRideLocationDTO());
        locations.put("destionation", new ResponsePanicRideLocationDTO());
        ArrayList<ResponsePanicRidePassengerDTO> passengers = new ArrayList<>();
        for (Passenger p:this.passengers) {
            passengers.add(p.parseToPanicPassengersDTO());
        }
        return new ResponsePanicRideDTO(this.id, this.startTime, this.endTime, this.totalCost, this.driver.parseToPanicDriverResponse(), passengers, this.estimatedTimeInMinutes, this.vehicleType, this.babyTransport, this.petTransport, new ResponsePanicRejectionDTO("reason1", new Date()), locations);
    }

}
