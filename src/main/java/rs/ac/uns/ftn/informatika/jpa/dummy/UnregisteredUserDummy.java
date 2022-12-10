package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.UnregisteredUser;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UnregisteredUserDummy {
    public static AtomicLong counter;
    public ConcurrentHashMap<Long, UnregisteredUser> unregisteredUsers;

    public UnregisteredUserDummy() {
        this.counter = new AtomicLong();
        this.unregisteredUsers = new ConcurrentHashMap<Long, UnregisteredUser>();
    }
}
