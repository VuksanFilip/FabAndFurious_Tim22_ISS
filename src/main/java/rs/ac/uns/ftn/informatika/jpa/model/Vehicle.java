package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseCurrentLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverVehicleDTO;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
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

    public ResponseDriverVehicleDTO parseToResponse(){
        return new ResponseDriverVehicleDTO(this.id,
                this.driver.getId(),
                this.vehicleType.getVehicleName(),
                this.vehicleModel,
                this.licenseNumber,
                new ResponseCurrentLocationDTO(this.currentLocation.getAddress(),
                        this.currentLocation.getLatitude(),
                        this.currentLocation.getLongitude()),
                this.seats,
                this.babyFriendly,
                this.petFriendly);
    }

    public void updateCurrentLocation(Location location) {
        this.getCurrentLocation().setAddress(location.getAddress());
        this.getCurrentLocation().setLatitude(location.getLatitude());
        this.getCurrentLocation().setLongitude(location.getLongitude());
    }
}
