package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.UserActivation;
import rs.ac.uns.ftn.informatika.jpa.repository.UserActivationRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserActivationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserActivationServiceImpl implements IUserActivationService {

    private UserActivationRepository userActivationRepository;

    @Autowired
    public UserActivationServiceImpl(UserActivationRepository userActivationRepository) {
        this.userActivationRepository = userActivationRepository;
    }

    public List<UserActivation> getAll() {
        return (List<UserActivation>) this.userActivationRepository.findAll();
    }

    @Override
    public Optional<UserActivation> getUserActivation(String id) {
        return  this.userActivationRepository.findById(Long.parseLong(id));
    }

    public UserActivation findUserActivationByUserId(String id){
        return this.userActivationRepository.findUserActivationByUserId(Long.parseLong(id));
    }

    public void add(UserActivation userActivation) {
        this.userActivationRepository.save(userActivation);
    }

    @Override
    public void delete(UserActivation activation) {
        userActivationRepository.delete(activation);
    }

    public void renewActivation(UserActivation activation) {
        activation.setDate(LocalDateTime.now());
        activation.setLifespan(3);
        this.add(activation);
    }
}
