package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.UnregisteredUser;
import rs.ac.uns.ftn.informatika.jpa.repository.RideRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UnregisteredUserRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.RideService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.UnregisteredUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UnregisteredUserServiceImpl implements UnregisteredUserService {

    private UnregisteredUserRepository unregisteredUserRepository;

    @Autowired
    public UnregisteredUserServiceImpl(UnregisteredUserRepository unregisteredUserRepository) {
        this.unregisteredUserRepository = unregisteredUserRepository;
    }

    public List<UnregisteredUser> getAll() {
        return (List<UnregisteredUser>) this.unregisteredUserRepository.findAll();
    }

    @Override
    public Optional<UnregisteredUser> getUnregisteredUser(String id) {
        return  this.unregisteredUserRepository.findById(Long.parseLong(id));
    }

    public void add(UnregisteredUser unregisteredUser) {
        this.unregisteredUserRepository.save(unregisteredUser);
    }
}
