package rs.ac.uns.ftn.informatika.jpa.model;

import java.util.Date;

public class WorkHour {
    Long id;
    String start;
    String end;
    Driver driver;

    public WorkHour() {
    }



    public WorkHour(Long id, Long driverId, String start, String end) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.driver = new Driver(driverId);
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
}
