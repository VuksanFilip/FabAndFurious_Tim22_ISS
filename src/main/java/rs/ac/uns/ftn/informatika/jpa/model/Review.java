package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.PassengerResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ReviewResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.ReviewResponseVehicleDTO;

public class Review {

     int totalCount;
     private Long id;
     float rating;
     String comment;
     Ride ride;
     Passenger passenger;

     Long vehicleId;

     public Review() {
     }

     public Review(Long id, float rating, String comment, Passenger passenger, Long rideId, Long vehicleId) {
          this.id = id;
          this.rating = rating;
          this.comment = comment;
          this.passenger = passenger;
          this.ride = new Ride();
          this.ride.setId(rideId);
          this.vehicleId = vehicleId;
     }

     public Review(Long id, float rating, String comment) {
          this.id = id;
          this.rating = rating;
          this.comment = comment;
          this.passenger = null;
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

     public ReviewResponseDTO parseToResponse(int totalCount){
          return new ReviewResponseDTO(this.id, this.rating, this.comment);
     }

     public ReviewResponseVehicleDTO parseToResponseVehicle(int totalCount, Long id){
          return new ReviewResponseVehicleDTO(totalCount, id, this.rating, this.comment);
     }

}
