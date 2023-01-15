package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return (List<User>) this.userRepository.findAll();
    }

    public Page<User> findAll(Pageable page) {
        return userRepository.findAll(page);
    }

    @Override
    public Optional<User> getUser(String id) {
        return  this.userRepository.findById(Long.parseLong(id));
    }

    public void add(User user) {
        this.userRepository.save(user);
    }

    public boolean existsById(String id) {
        return  this.userRepository.existsById(Long.parseLong(id));
    }
}
