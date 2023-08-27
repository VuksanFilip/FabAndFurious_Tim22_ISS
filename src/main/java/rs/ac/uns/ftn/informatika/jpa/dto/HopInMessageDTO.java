package rs.ac.uns.ftn.informatika.jpa.dto;

public class HopInMessageDTO {

    String message;
    Long rideId;

    public HopInMessageDTO(String message, Long rideId) {
        super();
        this.message = message;
        this.rideId = rideId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}