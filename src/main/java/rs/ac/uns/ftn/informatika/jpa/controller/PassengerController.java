package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger1;
//import rs.ac.uns.ftn.informatika.jpa.service.PassengerService;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController{

//    @Autowired
//    private PassengerService passengerService;


    private static AtomicLong counter = new AtomicLong();
    private ConcurrentHashMap<Long, Passenger1> passengers = new ConcurrentHashMap<Long, Passenger1>();

    @GetMapping
        public Collection<Passenger1> getAllPassengers(){
            return passengers.values();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Passenger1> getPassenger(@PathVariable("id") Long id) {
        Passenger1 passenger = passengers.get(id);

        if (passenger == null) {
            return new ResponseEntity<Passenger1>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Passenger1>(passenger, HttpStatus.OK);
    }

//    @GetMapping
//    public ResponseEntity<List<Passenger1>> getStudentsPage(Pageable page) {
//
//        // page object holds data about pagination and sorting
//        // the object is created based on the url parameters "page", "size" and "sort"
//        Page<Passenger1> passengers = passengerService.findAll(page);
//
//        // convert students to DTOs
//        List<Passenger1> passengersList = new ArrayList<>();
//        for (Passenger1 p : passengers) {
//            passengersList.add(p);
//        }
//
//        return new ResponseEntity<>(passengersList, HttpStatus.OK);
//    }



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Passenger1> createPassenger(@RequestBody Passenger1 passenger1) throws Exception {
        Long id = passenger1.getId();

        if (id == null) {
            id = counter.incrementAndGet();
            passenger1.setId(id);
        }

        passengers.put(id, passenger1);
        return new ResponseEntity<Passenger1>(passenger1, HttpStatus.CREATED);

    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Passenger1> updatePassenger(@RequestBody Passenger1 passenger, @PathVariable Long id)
            throws Exception {
        Passenger1 passengerForUpdate = passengers.get(id);
        passengerForUpdate.copyValues(passenger);

        Long idUpdate = passengerForUpdate.getId();
        if (idUpdate != null) {
            passengers.put(idUpdate, passengerForUpdate);
        }

        if (passengerForUpdate == null) {
            return new ResponseEntity<Passenger1>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Passenger1>(passengerForUpdate, HttpStatus.OK);
    }

    @PutMapping(value = "/activate/{id}")
    public ResponseEntity<Passenger1> activatePassenger(@PathVariable Long id) throws Exception {
        Passenger1 passenger = passengers.get(id);
        passenger.setActive(true);

        if (passenger == null) {
            return new ResponseEntity<Passenger1>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Passenger1>(passenger, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Passenger1> deletePassenger(@PathVariable("id") Long id) {
        passengers.remove(id);
        return new ResponseEntity<Passenger1>(HttpStatus.NO_CONTENT);
    }

}
