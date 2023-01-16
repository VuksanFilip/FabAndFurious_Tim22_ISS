package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.WorkingHour;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class WorkHourDummy {
    public static AtomicLong counter;
    public ConcurrentHashMap<Long, WorkingHour> workinghours;

    public WorkHourDummy() {
        this.counter = new AtomicLong();
        this.workinghours = new ConcurrentHashMap<Long, WorkingHour>();
    }
}
