package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.Date;

public class PanicSmallerDataResponseDTO {

    private Long id;
    private UserResponseDTO user;
    private RideResponseNoStatusDTO ride;
    private Date time;
    private String reason;

    public PanicSmallerDataResponseDTO() {
    }

    public PanicSmallerDataResponseDTO(Long id, UserResponseDTO user, RideResponseNoStatusDTO ride, Date time, String reason) {
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

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public RideResponseNoStatusDTO getRide() {
        return ride;
    }

    public void setRide(RideResponseNoStatusDTO ride) {
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
