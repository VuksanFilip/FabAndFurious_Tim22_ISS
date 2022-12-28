package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.repository.PassengerRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.PassengerService;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {

    private PassengerRepository passengerRepository;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public List<Passenger> getAll() {
        return (List<Passenger>) this.passengerRepository.findAll();
    }

    @Override
    public Optional<Passenger> getPassenger(String id) {
        return  this.passengerRepository.findById(Long.parseLong(id));
    }

    public void add(Passenger passenger) {
        this.passengerRepository.save(passenger);
    }
}
