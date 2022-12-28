package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.UnregisteredUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnregisteredUserRepository extends CrudRepository<UnregisteredUser, Long> {

    List<UnregisteredUser> findAll();
    Optional<UnregisteredUser> findById(String Long);

}