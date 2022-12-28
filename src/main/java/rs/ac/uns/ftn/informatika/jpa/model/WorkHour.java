package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverWorkingHourDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class WorkHour {

    @Id
    Long id;
    String start;
    String end;

    @OneToOne
    Driver driver;

    public WorkHour() {
    }

    public WorkHour(String start, String end, Driver driver) {
        this.start = start;
        this.end = end;
        this.driver = driver;
    }

    public WorkHour(Long id, String start, String end) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.driver = new Driver();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public ResponseDriverWorkingHourDTO parseToResponse(){
        return new ResponseDriverWorkingHourDTO(this.getId(), this.getStart(), this.getEnd());
    }
}
