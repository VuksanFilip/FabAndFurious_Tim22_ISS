package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Panic;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.PanicRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPanicService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PanicServiceImpl implements IPanicService {

    private PanicRepository panicRepository;

    @Autowired
    public PanicServiceImpl(PanicRepository panicRepository) {
        this.panicRepository = panicRepository;
    }

    public List<Panic> getAll() {
        return (List<Panic>) this.panicRepository.findAll();
    }

    @Override
    public Optional<Panic> getPanic(String id) {
        return  this.panicRepository.findById(Long.parseLong(id));
    }

    public void add(Panic panic) {
        this.panicRepository.save(panic);
    }

    public Panic createPanicByRide(Ride ride, String reason){
        Panic panic = new Panic(new User(), ride, new Date(), reason);
        return panic;
    }
}
