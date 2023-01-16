package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Panic;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.PanicRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPanicService;

import java.util.Date;
import java.util.Calendar;
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

    public Page<Panic> findAll(Pageable page) {
        return panicRepository.findAll(page);
    }

    @Override
    public Optional<Panic> getPanic(String id) {
        return  this.panicRepository.findById(Long.parseLong(id));
    }

    public void add(Panic panic) {
        this.panicRepository.save(panic);
    }

    public Panic createPanicByRide(User user, Ride ride, String reason){
        return new Panic(user, ride, new Date(Calendar.getInstance().getTime().getTime()), reason);
    }
}
