package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.UserActivation;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserActivationRepository extends CrudRepository<UserActivation, Long> {

    List<UserActivation> findAll();
    Optional<UserActivation> findById(String Long);

}