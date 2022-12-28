package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Departure;
import rs.ac.uns.ftn.informatika.jpa.repository.DepartureRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.DepartureService;

import java.util.List;
import java.util.Optional;

@Service
public class DepartureServiceImpl implements DepartureService {

    private DepartureRepository departureRepository;

    @Autowired
    public DepartureServiceImpl(DepartureRepository departureRepository) {
        this.departureRepository = departureRepository;
    }

    public List<Departure> getAll() {
        return (List<Departure>) this.departureRepository.findAll();
    }

    @Override
    public Optional<Departure> getDeparture(String id) {
        return  this.departureRepository.findById(Long.parseLong(id));
    }

    public void add(Departure departure) {
        this.departureRepository.save(departure);
    }
}
