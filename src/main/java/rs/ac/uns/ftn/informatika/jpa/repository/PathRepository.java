package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.Path;

import java.util.List;
import java.util.Optional;

@Repository
public interface PathRepository extends CrudRepository<Path, Long> {

    List<Path> findAll();
    Optional<Path> findById(String Long);

}