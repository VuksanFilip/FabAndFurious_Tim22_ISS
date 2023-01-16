package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserService {

    List<User> getAll();

    Optional<User> getUser(String id);

    Page<User> findAll(Pageable page);

    void add(User user);

    Set<ResponseMessageDTO> findMessagesOfUser(String id);
}
