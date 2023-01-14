package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteLocations;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteLocationsRepository extends CrudRepository<FavoriteLocations, Long> {

    List<FavoriteLocations> findAll();
    Optional<FavoriteLocations> findById(String Long);
    long count();
    FavoriteLocations deleteById(String Long);
    boolean existsById(String Long);
}
