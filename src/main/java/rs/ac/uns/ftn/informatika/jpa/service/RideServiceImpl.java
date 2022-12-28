package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.repository.RideRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.RideService;

import java.util.List;
import java.util.Optional;

@Service
public class RideServiceImpl implements RideService {

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

    public void add(Ride ride) {
        this.rideRepository.save(ride);
    }
}
