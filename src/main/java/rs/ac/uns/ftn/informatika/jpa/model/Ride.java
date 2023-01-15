package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Entity
public class Ride {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Date startTime;
    private Date endTime;
    private int totalCost;


    @OneToOne(cascade = {CascadeType.ALL})
    private Driver driver;

    @ManyToMany
    @Column(name = "passenger_id")
    @JoinTable(name = "Ride_Passenger",
            joinColumns = { @JoinColumn(name = "ride_id") },
            inverseJoinColumns = { @JoinColumn(name = "passenger_id") }
    )
    private List<Passenger> passengers;


    @OneToMany(cascade = {CascadeType.ALL})
    private List<Location> locations;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Path> paths;
    private int estimatedTimeInMinutes;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Review> reviews;

    @OneToOne(cascade = {CascadeType.ALL})
    private RejectionLetter letter;
    private boolean panic;
    private boolean babyTransport;
    private boolean petTransport;

    @OneToOne(cascade = {CascadeType.ALL})
    private Vehicle vehicle;
    private RideStatus status;

    public Ride() {
    }

    public Ride(Driver driver, List<Passenger> passengers) {
        this.driver = driver;
        this.passengers = passengers;
    }

    public Ride(List<Location> locations, ArrayList<Passenger> passengers, Vehicle vehicle, boolean babyTransport, boolean petTransport) {
        this.passengers = passengers;
        this.locations = locations;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.vehicle = vehicle;
    }

    public Ride(Long id, List<Location> locations, ArrayList<Passenger> passengers, Vehicle vehicle, boolean babyTransport, boolean petTransport) {
        this.id = id;
        this.passengers = passengers;
        this.locations = locations;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.vehicle = vehicle;
    }

    public Ride(List<Location> locations, ArrayList<Passenger> passengers, Vehicle vehicle, boolean babyTransport, boolean petTransport, Driver driver, RejectionLetter letter, RideStatus status) {
        this.passengers = passengers;
        this.locations = locations;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.vehicle = vehicle;
        this.driver = driver;
        this.letter = letter;
        this.status = status;
    }

    public Ride(Long id, List<Location> locations, List<Passenger> passengers, Vehicle vehicle, boolean babyTransport, boolean petTransport, Driver driver){
        this.id = id;
        this.passengers = passengers;
        this.locations = locations;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.vehicle = vehicle;
        this.driver = driver;
    }



    public Ride(Date startTime, Date endTime, int totalCost, Driver driver, List<Passenger> passengers, List<Location> locations, List<Path> paths, int estimatedTimeInMinutes, List<Review> reviews, RejectionLetter letter, boolean panic, boolean babyTransport, boolean petTransport, Vehicle vehicle, RideStatus status) {
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
        this.vehicle = vehicle;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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
        this.vehicle = ride.getVehicle();
        this.status = ride.getStatus();
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

        ResponseRideDTO rideResponse = new ResponseRideDTO(this.id, responsPassengerIdEmailDTOS, this.vehicle.vehicleType.type, this.babyTransport, this.petTransport, responseLocationDTOS);

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

        ResponseRideDTO rideResponse = new ResponseRideDTO(this.id, this.startTime, this.endTime, this.totalCost, new ResponseDriverIdEmailDTO(this.driver.getId(), this.driver.getEmail()), responsPassengerIdEmailDTOS, this.estimatedTimeInMinutes, this.vehicle.vehicleType.type, this.babyTransport, this.petTransport, new ResponseRejectionReasonTimeOfDetectionDTO(this.letter.getReason(), this.letter.getTime()), responseLocationDTOS, this.status);

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
        ResponseRideDTO rideResponse = new ResponseRideDTO(this.id, responsPassengerIdEmailDTOS, this.vehicle.vehicleType.type, this.babyTransport, this.petTransport, responseLocationDTOS, this.status);
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
        ResponseRideDTO rideResponse = new ResponseRideDTO(this.id, responsPassengerIdEmailDTOS, this.vehicle.vehicleType.type, this.babyTransport, this.petTransport, responseLocationDTOS, this.status, new ResponseRejectionReasonTimeOfDetectionDTO(this.letter.reason, this.letter.time));
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
        ResponseRideNoStatusDTO rideResponse = new ResponseRideNoStatusDTO(this.id, responsPassengerIdEmailDTOS, this.vehicle.vehicleType.type, this.babyTransport, this.petTransport, responseLocationDTOS);
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
        return new ResponsePanicRideDTO(this.id, this.startTime, this.endTime, this.totalCost, this.driver.parseToPanicDriverResponse(), passengers, this.estimatedTimeInMinutes, this.vehicle.vehicleType, this.babyTransport, this.petTransport, new ResponsePanicRejectionDTO("reason1", new Date()), locations);
    }

}
