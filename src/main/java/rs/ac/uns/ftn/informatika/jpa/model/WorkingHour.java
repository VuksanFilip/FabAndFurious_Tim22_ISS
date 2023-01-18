package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestDriverWorkingHourStartDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverWorkingHourDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.Calendar;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class WorkingHour {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Date start;
    private Date end;

    @OneToOne
    private Driver driver;

    public WorkingHour(Date start, Date end, Driver driver) {
        this.start = start;
        this.end = end;
        this.driver = driver;
    }

    public WorkingHour(Long id, Date start, Date end) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.driver = new Driver();
    }

    public WorkingHour(Driver driver, RequestDriverWorkingHourStartDTO dto){
        this.start = dto.getStart();
        this.end = new Date(Calendar.getInstance().getTime().getTime());
        this.driver = driver;
    }

    public ResponseDriverWorkingHourDTO parseToResponse() {
        return new ResponseDriverWorkingHourDTO(this.id, this.start, this.end);
    }
}
