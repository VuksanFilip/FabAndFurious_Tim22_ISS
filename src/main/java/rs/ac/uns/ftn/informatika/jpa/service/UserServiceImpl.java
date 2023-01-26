package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.MessageRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;
    private MessageRepository messageRepository;

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

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }


    @Override
    public Set<ResponseMessageDTO> findMessagesOfUser(String id) {
        Optional<User> userO = userRepository.findById(id);
        if(!userO.isPresent()){
            return null;
        }
        User user = userO.get();
        Set<Message> messages = messageRepository.findBySender(user);
        Set<ResponseMessageDTO> messageDTOS = new HashSet<>();
        for(Message m : messages){
            messageDTOS.add(m.parseToDTO());
        }
        return messageDTOS;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return user;
        }
    }
}
