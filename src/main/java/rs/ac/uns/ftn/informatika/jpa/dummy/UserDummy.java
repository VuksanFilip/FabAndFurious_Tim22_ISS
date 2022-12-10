package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserDummy {

    public static AtomicLong counter;
    public ConcurrentHashMap<Long, User> users;

    public UserDummy() {
        this.counter = new AtomicLong();
        this.users = new ConcurrentHashMap<Long, User>();
    }
}
