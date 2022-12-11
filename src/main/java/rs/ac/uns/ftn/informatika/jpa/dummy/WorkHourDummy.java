package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Document;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.model.WorkHour;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class WorkHourDummy {
    public static AtomicLong counter;
    public ConcurrentHashMap<Long, WorkHour> workinghours;

    public WorkHourDummy() {
        this.counter = new AtomicLong();
        this.workinghours = new ConcurrentHashMap<Long, WorkHour>();
    }
}
