package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicSmallerDataDTO;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Panic {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToOne
    private Ride ride;
    private Date time;
    private String reason;

    public Panic(User user, Ride ride, Date time, String reason) {
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.reason = reason;
    }

    public ResponsePanicSmallerDataDTO parseToResponseSmallerData(){
        return new ResponsePanicSmallerDataDTO(this.id, this.user.parseToResponseUser(), this.ride.parseToResponseNoStatus(), this.time, this.reason);
    }

    public ResponsePanicDTO parseToResponse(){
        return new ResponsePanicDTO(this.id, this.user.parseToPanicResponse(), this.ride.parseToResponseNoStatus(), this.time, this.reason);
    }
}
