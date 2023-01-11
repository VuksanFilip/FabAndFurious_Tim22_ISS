package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Destination;
import rs.ac.uns.ftn.informatika.jpa.repository.DestinationRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IDestinationService;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationServiceImpl implements IDestinationService {

    private DestinationRepository destinationRepository;

    @Autowired
    public DestinationServiceImpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    public List<Destination> getAll() {
        return (List<Destination>) this.destinationRepository.findAll();
    }

    @Override
    public Optional<Destination> getDestination(String id) {
        return  this.destinationRepository.findById(Long.parseLong(id));
    }

    public void add(Destination destination) {
        this.destinationRepository.save(destination);
    }
}
