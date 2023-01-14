package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReviewDriverDTO;

import javax.persistence.*;

@Entity
public class Review {

     @Id
     @GeneratedValue(strategy= GenerationType.IDENTITY)
     private Long id;
     float rating;
     String comment;

     @ManyToOne
     Ride ride;

     @OneToOne(cascade = {CascadeType.ALL})
     Passenger passenger;

     Long vehicleId;
     Long driverId;

     public Review(float rating, String comment, Ride ride) {
          this.rating = rating;
          this.comment = comment;
          this.ride = ride;
     }

     public Review(Long id) {
          this.id = id;
     }

     public Review(){}

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

     public float getRating() {
          return rating;
     }

     public void setRating(float rating) {
          this.rating = rating;
     }

     public Long getDriverId() {
          return driverId;
     }

     public void setDriverId(Long driverId) {
          this.driverId = driverId;
     }

     public String getComment() {
          return comment;
     }

     public void setComment(String comment) {
          this.comment = comment;
     }

     public Ride getRide() {
          return ride;
     }

     public void setRide(Ride ride) {
          this.ride = ride;
     }

     public Passenger getPassenger() {
          return passenger;
     }

     public void setPassenger(Passenger passenger) {
          this.passenger = passenger;
     }

     public Long getVehicleId() {
          return vehicleId;
     }

     public void setVehicleId(Long vehicleId) {
          this.vehicleId = vehicleId;
     }

     public ResponseReviewDriverDTO parseToResponseDriver(int totalCount, Long id){
          return new ResponseReviewDriverDTO(totalCount, id, this.rating, this.comment);
     }

     public ResponseReviewDTO parseToResponse(){
          return new ResponseReviewDTO(this.id, this.rating, this.comment, new ResponsePassengerIdEmailDTO(this.passenger));
     }

}
