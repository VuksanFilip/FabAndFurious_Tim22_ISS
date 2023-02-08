package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
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
    private List<Route> routes;
    private int estimatedTimeInMinutes;

    @OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToOne(cascade = {CascadeType.ALL})
    private RejectionLetter letter;
    private boolean panic;
    private boolean babyTransport;
    private boolean petTransport;

    @OneToOne(cascade = {CascadeType.ALL})
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private Date scheduledTime;

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

    public Ride(Date startTime, Date endTime, int totalCost, Driver driver, List<Passenger> passengers, List<Location> locations, List<Route> routes, int estimatedTimeInMinutes, List<Review> reviews, RejectionLetter letter, boolean panic, boolean babyTransport, boolean petTransport, Vehicle vehicle, RideStatus status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.locations = locations;
        this.routes = routes;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.reviews = reviews;
        this.letter = letter;
        this.panic = panic;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.vehicle = vehicle;
        this.status = status;
    }

    public Ride(Driver driver, List<Passenger> passengers, List<Route> routes, boolean babyTransport, boolean petTransport, Date scheduledTime, Vehicle vehicle){
        this.driver = driver;
        this.passengers = passengers;
        this.routes = routes;
        this.panic = false;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.status = RideStatus.PENDING;
        this.scheduledTime = scheduledTime;
        this.vehicle = vehicle;
    }

    public Ride(Date startTime, Date endTime, int totalCost, int estimatedTimeInMinutes, boolean panic, boolean petTransport, boolean babyTransport, RideStatus status, Date scheduledTime){
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.panic = panic;
        this.petTransport = petTransport;
        this.babyTransport = babyTransport;
        this.status = status;
        this.scheduledTime = scheduledTime;
    }


    public ResponseRideNoStatusDTO parseToResponseNoStatus(){

        ArrayList<ResponsePassengerIdEmailDTO> responsPassengerIdEmailDTOS = new ArrayList<ResponsePassengerIdEmailDTO>();
        for(Passenger p : passengers){
            responsPassengerIdEmailDTOS.add(new ResponsePassengerIdEmailDTO(p.getId(), p.getEmail()));
        }

        ArrayList<ResponseLocationDTO> responseLocationDTOS = new ArrayList<ResponseLocationDTO>();
        for(Route r : routes){
            responseLocationDTOS.add(new ResponseLocationDTO(r.getDeparture().parseToResponse(), r.getDestination().parseToResponse()));
        }

        ResponseDriverIdEmailDTO responseDriverIdEmailDTO = new ResponseDriverIdEmailDTO(driver.getId(), driver.getEmail());

        if(this.letter == null){
            return new ResponseRideNoStatusDTO(this, responsPassengerIdEmailDTOS, responseLocationDTOS, responseDriverIdEmailDTO, null);
        }

        return new ResponseRideNoStatusDTO(this, responsPassengerIdEmailDTOS, responseLocationDTOS, responseDriverIdEmailDTO, this.letter.parseToResponse());
    }

    public ResponseRideNewDTO parseToResponse() {

        List<ResponsePassengerIdEmailDTO> passengers = new ArrayList<>();
        for(Passenger p : this.passengers){
            passengers.add(p.parseToResponseIdEmail());
        }

        List<RequestLocationDTO> locations = new ArrayList<>();
        for(Route r : this.routes){
            locations.add(r.parseToResponse());
        }

        if(this.letter != null){
            return new ResponseRideNewDTO(this.id, this.startTime, this.endTime, this.totalCost, this.driver.parseToResponseIdEmail(), passengers, this.estimatedTimeInMinutes, this.driver.getVehicle().getVehicleType().getVehicleName(), this.babyTransport, this.petTransport, this.letter.parseToResponse(), locations, this.status, this.scheduledTime);
        }

        return new ResponseRideNewDTO(this.id, this.startTime, this.endTime, this.totalCost, this.driver.parseToResponseIdEmail(), passengers, this.estimatedTimeInMinutes, this.driver.getVehicle().getVehicleType().getVehicleName(), this.babyTransport, this.petTransport, null, locations, this.status, scheduledTime);
    }
}
