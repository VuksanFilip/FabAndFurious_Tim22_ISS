package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getAll();

    Optional<User> getUser(String id);

    void add(User user);
}
