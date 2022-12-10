package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Panic;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PanicDummy {
    public static AtomicLong counter;
    public ConcurrentHashMap<Long, Panic> panics;

    public PanicDummy() {
        this.counter = new AtomicLong();
        this.panics = new ConcurrentHashMap<Long, Panic>();
    }
}
