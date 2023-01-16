package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.repository.PassengerRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPassengerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements IPassengerService {

    private PassengerRepository passengerRepository;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getAll() {
        return this.passengerRepository.findAll();
    }

    public List<Passenger> getAllWithStatusPending() {
        List<Passenger> passengers = new ArrayList<>();
        for (Passenger p : getAll()) {
            for (Ride r : p.getRides()) {
                if (r.getStatus() == RideStatus.PENDING) {
                    passengers.add(p);
                }
            }
        }
        return passengers;
    }

    public Page<Passenger> findAll(Pageable page) {
        return passengerRepository.findAll(page);
    }

    public boolean existsById(String id) {
        return  this.passengerRepository.existsById(Long.parseLong(id));
    }

    @Override
    public Passenger findByEmail(String email) {
        return passengerRepository.findByEmail(email);
    }

    @Override
    public Optional<Passenger> getPassenger(String id) {
        return  this.passengerRepository.findById(Long.parseLong(id));
    }

    public void add(Passenger passenger) {
        this.passengerRepository.save(passenger);
    }
}
