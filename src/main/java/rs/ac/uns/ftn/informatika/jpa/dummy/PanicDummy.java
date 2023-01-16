package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseAllPanicsDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PanicDummy {

    public static AtomicLong counter;
    public ConcurrentHashMap<Long, Panic> panics;

    public PanicDummy() {
        this.counter = new AtomicLong();
        this.panics = new ConcurrentHashMap<Long, Panic>();
        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger());
        passengers.add(new Passenger());
        passengers.add(new Passenger());
        this.panics.put(Long.valueOf(1), new Panic(Long.valueOf(1), new Date(Calendar.getInstance().getTime().getTime()),"reasonn", new User(), new Ride(new Driver(), passengers)));
        this.panics.put(Long.valueOf(2), new Panic(Long.valueOf(2), new Date(Calendar.getInstance().getTime().getTime()),"reasonn2", new User(), new Ride(new Driver(), passengers)));
    }

    public ResponseAllPanicsDTO parseToResponse(){
        ArrayList<ResponsePanicDTO> panics = new ArrayList<>();
        for (Long id:this.panics.keySet()) {
            panics.add(this.panics.get(id).parseToResponse());
        }
        return new ResponseAllPanicsDTO(panics.size(), panics);
    }
}
