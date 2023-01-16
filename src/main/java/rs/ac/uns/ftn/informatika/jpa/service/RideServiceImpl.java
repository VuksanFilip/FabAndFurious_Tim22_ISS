package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.RejectionLetter;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.repository.RideRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRideService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RideServiceImpl implements IRideService {

    private RideRepository rideRepository;

    @Autowired
    public RideServiceImpl(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public List<Ride> getAll() {
        return (List<Ride>) this.rideRepository.findAll();
    }

    @Override
    public Optional<Ride> getRide(String id) {
        return  this.rideRepository.findById(Long.parseLong(id));
    }

    @Override
    public boolean existsById(String id) {
        return  this.rideRepository.existsById(Long.parseLong(id));
    }

    public Page<Ride> findAll(Pageable page) {
        return rideRepository.findAll(page);
    }

    @Override
    public List<Ride> getRidesOfDriver(Driver driver) {
        List<Ride> allRides = new ArrayList<>();
        for(Ride r : getAll()){
            if(r.getDriver() == driver){
                allRides.add(r);
            }
        }
        return allRides;
    }

    @Override
    public Ride updateRide(Ride ride) {
        return  this.rideRepository.save(ride);
    }

    public void add(Ride ride) {
        this.rideRepository.save(ride);
    }

    public long getSize() { return this.rideRepository.count(); }

    public Ride getaActiveRideByDriverId(String id){
        for(Ride ride : getAll()){
            if((ride.getDriver().getId() == Long.parseLong(id)) && (ride.getStatus() == RideStatus.STARTED)){
                return ride;
            }
        }
        return null;
    }

    public Ride getActiveRideByPassengerId(String id){
        for(Ride ride : getAll()){
            for(Passenger passenger : ride.getPassengers()){
                if(passenger.getId() == Long.parseLong(id) && (ride.getStatus() == RideStatus.STARTED)){
                    return ride;
                }
            }
        }
        return null;
    }

    public void updateRideByStatus(String id, RideStatus status){
        Optional<Ride> ride = getRide(id);
        ride.get().setStatus(status);
        this.rideRepository.save(ride.get());
    }

    public void updateRideByRejectionLetter(String id, RejectionLetter letter){
        Optional<Ride> ride = getRide(id);
        ride.get().setLetter(letter);
        this.rideRepository.save(ride.get());
    }
}
