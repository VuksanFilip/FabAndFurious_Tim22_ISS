package rs.ac.uns.ftn.informatika.jpa.dummy;

<<<<<<< Updated upstream
import rs.ac.uns.ftn.informatika.jpa.model.Panic;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
=======
import rs.ac.uns.ftn.informatika.jpa.dto.response.AllPanicsResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.PanicResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;
>>>>>>> Stashed changes

import java.util.ArrayList;
import java.util.Date;
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
        this.panics.put(Long.valueOf(1), new Panic(Long.valueOf(1), new Date(),"reasonn", new User(), new Ride(passengers, new Driver())));
        this.panics.put(Long.valueOf(2), new Panic(Long.valueOf(2), new Date(),"reasonn2", new User(), new Ride(passengers, new Driver())));
    }

    public AllPanicsResponseDTO parseToResponse(){
        ArrayList<PanicResponseDTO> panics = new ArrayList<>();
        for (Long id:this.panics.keySet()) {
            panics.add(this.panics.get(id).parseToResponse());
        }
        return new AllPanicsResponseDTO(panics.size(), panics);
    }
}
