package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.ReviewType;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Review {

     @Id
     @GeneratedValue(strategy= GenerationType.IDENTITY)
     private Long id;
     private float rating;
     private String comment;

     @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
     private Ride ride;

     @OneToOne(cascade = {CascadeType.ALL})
     private Passenger passenger;

     @Enumerated(EnumType.STRING)
     private ReviewType reviewType;

     public Review(float rating, String comment, Ride ride) {
          this.rating = rating;
          this.comment = comment;
          this.ride = ride;
     }

     public Review(Long id) {
          this.id = id;
     }


     public Review(Long id, float rating, String comment, Passenger passenger, Long rideId, Long vehicleId, String str) {
          this.id = id;
          this.rating = rating;
          this.comment = comment;
          this.passenger = passenger;
          this.ride = new Ride();
          this.ride.setId(rideId);
          this.ride.setDriver(new Driver());
          this.ride.getDriver().setVehicle(new Vehicle(vehicleId));
     }

     public Review(float rating, String comment, Passenger passenger, Long rideId, Long vehicleId) {
          this.rating = rating;
          this.comment = comment;
          this.passenger = passenger;
          this.ride = new Ride();
          this.ride.setId(rideId);
          this.ride.setDriver(new Driver());
          this.ride.getDriver().setVehicle(new Vehicle(vehicleId));
     }


     public Review(Long id, float rating, String comment, Passenger passenger, Long rideId, String str, Long driverId) {
          this.id = id;
          this.rating = rating;
          this.comment = comment;
          this.passenger = passenger;
          this.ride = new Ride();
          this.ride.setId(rideId);
          this.ride.setDriver(new Driver(driverId));
     }

     public Review(float rating, String comment, Passenger passenger, Long rideId, String str, Long driverId) {
          this.rating = rating;
          this.comment = comment;
          this.passenger = passenger;
          this.ride = new Ride();
          this.ride.setId(rideId);
          this.ride.setDriver(new Driver(driverId));
     }

     public Review(Long id, float rating, String comment) {
          this.id = id;
          this.rating = rating;
          this.comment = comment;
          this.passenger = null;
     }

     public Review(Long id, float rating, String comment, Ride ride, Passenger passenger) {
          this.id = id;
          this.rating = rating;
          this.comment = comment;
          this.ride = ride;
          this.passenger = passenger;
     }

     public Review(float rating, Ride ride, Passenger passenger) {
          this.rating = rating;
          this.ride = ride;
          this.passenger = passenger;
     }

     public Review(float rating, String comment, Ride ride, Passenger passenger) {
          this.rating = rating;
          this.comment = comment;
          this.ride = ride;
          this.passenger = passenger;
     }

     public Review(float rating, String comment, Ride ride, Passenger passenger, ReviewType reviewType) {
          this.rating = rating;
          this.comment = comment;
          this.ride = ride;
          this.passenger = passenger;
          this.reviewType = reviewType;
     }

     public ResponseReviewDTO parseToResponse(){
          return new ResponseReviewDTO(this.id, this.rating, this.comment, new ResponsePassengerIdEmailDTO(this.passenger.getId(), this.passenger.getEmail()));
     }

}
