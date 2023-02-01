package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestDriverWorkingHourStartDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverWorkingHourDTO;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime start;
    private LocalDateTime endTime;

    @OneToOne
    private Driver driver;

    public WorkingHour(LocalDateTime start, LocalDateTime endTime, Driver driver) {
        this.start = start;
        this.endTime = endTime;
        this.driver = driver;
    }

    public WorkingHour(Long id, LocalDateTime start, LocalDateTime endTime) {
        this.id = id;
        this.start = start;
        this.endTime = endTime;
        this.driver = new Driver();
    }

    public WorkingHour(Driver driver, RequestDriverWorkingHourStartDTO dto){
        this.start = dto.getStart();
        this.endTime = null;
        this.driver = driver;
    }

    public ResponseDriverWorkingHourDTO parseToResponse() {
        return new ResponseDriverWorkingHourDTO(this.id, this.start, this.endTime);
    }
}
