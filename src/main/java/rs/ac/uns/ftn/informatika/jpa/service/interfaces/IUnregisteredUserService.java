package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.UnregisteredUser;

import java.util.List;
import java.util.Optional;

public interface IUnregisteredUserService {

    List<UnregisteredUser> getAll();

    Optional<UnregisteredUser> getUnregisteredUser(String id);

    void add(UnregisteredUser unregisteredUser);
}
