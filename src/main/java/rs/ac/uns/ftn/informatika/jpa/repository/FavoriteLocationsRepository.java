package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoute;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteLocationsRepository extends CrudRepository<FavoriteRoute, Long> {

    List<FavoriteRoute> findAll();
    Optional<FavoriteRoute> findById(String Long);
    long count();
    FavoriteRoute deleteById(String Long);
    boolean existsById(String Long);
}
