package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.UserActivation;

import java.util.List;
import java.util.Optional;

public interface IUserActivationService {

    List<UserActivation> getAll();

    Optional<UserActivation> getUserActivation(String id);

    void add(UserActivation userActivation);

    void delete(UserActivation activation);

    void renewActivation(UserActivation activation);

    UserActivation findUserActivationByUserId(String id);
}
