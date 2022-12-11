package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.DriverVehicleResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.DriverWorkingHourResponseDTO;

import java.util.Date;

public class WorkHour {

    Long id;
    String start;
    String end;
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

    public DriverWorkingHourResponseDTO parseToResponse(){
        return new DriverWorkingHourResponseDTO(this.getId(), this.getStart(), this.getEnd());
    }
}
