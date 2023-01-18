package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRejectionReasonTimeOfDetectionDTO;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class RejectionLetter {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Ride ride;
    private String reason;

    @OneToOne
    private User user;
    private Date time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RejectionLetter(String reason) {
        this.reason = reason;
    }

    public RejectionLetter(String reason, Date time) {
        this.reason = reason;
        this.time = time;

    }

    public RejectionLetter(Ride ride, String reason, User user, Date time) {
        this.ride = ride;
        this.reason = reason;
        this.user = user;
        this.time = time;
    }

    public ResponseRejectionReasonTimeOfDetectionDTO parseToResponse() {
        return new ResponseRejectionReasonTimeOfDetectionDTO(this.reason, this.time);
    }
}
