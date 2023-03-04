package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService extends UserDetailsService {

    List<User> getAll();

    Optional<User> getUser(String id);

    Page<User> findAll(Pageable page);

    void add(User user);

//    Set<ResponseMessageDTO> findMessagesOfUser(String id);

    boolean existsById(String id);

    Optional<User> findByEmail(String email);

    void processOAuthPostLogin(String email, String name);
}
