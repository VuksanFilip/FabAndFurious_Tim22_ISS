package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.Date;

public class ResponsePanicDTO {

    private Long id;
    private ResponsePanicUserDTO user;
    private ResponseRideNoStatusDTO ride;
    private Date time;
    private String reason;

    public ResponsePanicDTO(Long id, ResponsePanicUserDTO user, ResponseRideNoStatusDTO ride, Date time, String reason) {
        this.id = id;
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResponsePanicUserDTO getUser() {
        return user;
    }

    public void setUser(ResponsePanicUserDTO user) {
        this.user = user;
    }

    public ResponseRideNoStatusDTO getRide() {
        return ride;
    }

    public void setRide(ResponseRideNoStatusDTO ride) {
        this.ride = ride;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
