package rs.ac.uns.ftn.informatika.jpa.modell;

import java.util.Date;

public class WorkHour {

    Date start;
    Date end;
    Driver driver;

    public WorkHour() {
    }

    public WorkHour(Date start, Date end, Driver driver) {
        this.start = start;
        this.end = end;
        this.driver = driver;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
