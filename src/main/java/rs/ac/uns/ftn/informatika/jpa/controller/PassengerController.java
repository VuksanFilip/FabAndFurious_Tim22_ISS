package rs.ac.uns.ftn.informatika.jpa.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestPassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPassengerService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRideService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/passenger")
public class PassengerController{

    private IPassengerService passengerService;
    private IRideService rideService;

    @Autowired
    public PassengerController(IPassengerService passengerService, IRideService rideService) {
        this.passengerService = passengerService;
        this.rideService = rideService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePassengerDTO> createPassenger(@RequestBody RequestPassengerDTO requestPassengerDTO) throws Exception {

        Passenger passenger =  requestPassengerDTO.parseToPassenger();
        passengerService.add(passenger);
        return new ResponseEntity<>(passenger.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponsePageDTO> getStudentsPage(Pageable page) {

        Page<Passenger> passengers = passengerService.findAll(page);
        int results = passengerService.getAll().size();

        List<ResponsePassengerDTO> responsePassengerDTOS = new ArrayList<>();
        for (Passenger p : passengers) {
            responsePassengerDTOS.add(new ResponsePassengerDTO(p));
        }
        return new ResponseEntity<>(new ResponsePageDTO(results, Arrays.asList(responsePassengerDTOS.toArray())), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePassengerDTO> getPassenger(@PathVariable("id") String id) {

        Optional<Passenger> passenger = this.passengerService.getPassenger(id);
        return new ResponseEntity<>(passenger.get().parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/activate/{activationId}")
    public ResponseEntity<String> activatePassenger(@PathVariable("activationId") String id) {
        if(passengerService.getPassenger(id).isPresent()){
            return new ResponseEntity<>("Message placeholder (Concrete messages are in description)", HttpStatus.OK);

        }
        return new ResponseEntity<>("Message placeholder (Concrete messages are in description)", HttpStatus.NOT_FOUND);
    }

    @PutMapping (value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePassengerDTO> updatePassenger(@PathVariable("id") String id, @RequestBody RequestPassengerDTO requestPassengerDTO) {
        Passenger passengerForUpdate = passengerService.getPassenger(id).get();
        Passenger passenger = requestPassengerDTO.parseToPassenger();
        passengerForUpdate.update(passenger);
        passengerService.add(passengerForUpdate);
        return new ResponseEntity<>(passengerForUpdate.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponsePageDTO> getPassengerRides(@PathVariable("id") String id, Pageable page) {

        //LOSIJA VERZIJA
        Page<Ride> rides = rideService.findAll(page);
        List<ResponseRideDTO> responseRideDTOS = new ArrayList<>();
        for(Ride r: rides){
            for(Passenger p: r.getPassengers()){
                if(p.getId() == Long.parseLong(id)){
                    responseRideDTOS.add(r.parseToResponse());
                }
            }
        }

        List<Ride> ridesForSize = rideService.getAll();
        int result = 0;
        for(Ride r: ridesForSize){
            for(Passenger p: r.getPassengers()){
                if(p.getId() == Long.parseLong(id)){
                    result = result + 1;
                }
            }
        }

        //BOLJA VERZIJA(SKONTATI RESENJE)
//      Passenger passenger = passengerService.getPassenger(id).get();
//      List<Ride> rides = passenger.getRides();
//      System.out.println(rides.size());
//      List<ResponseRideDTO> responseRideDTOS = new ArrayList<>();
//      for(Ride r : rides){
//          responseRideDTOS.add(r.parseToResponse());
//      }

        return new ResponseEntity<>(new ResponsePageDTO(result, Arrays.asList(responseRideDTOS.toArray())), HttpStatus.OK);

    }


}
