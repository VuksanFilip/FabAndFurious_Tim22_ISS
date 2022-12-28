package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.RejectionLetter;

import java.util.List;
import java.util.Optional;

@Repository
public interface RejectionLetterRepository extends CrudRepository<RejectionLetter, Long> {

    List<RejectionLetter> findAll();
    Optional<RejectionLetter> findById(String Long);

}