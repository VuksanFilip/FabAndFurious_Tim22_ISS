package rs.ac.uns.ftn.informatika.jpa.model;

public class Review {

     private Long id;
     float rating;
     String comment;
     Ride ride;
     Passenger passenger;

     public Review() {
     }

     public Review(Long id, float rating, String comment, Passenger passenger) {
          this.id = id;
          this.rating = rating;
          this.comment = comment;
          this.passenger = passenger;
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
}
