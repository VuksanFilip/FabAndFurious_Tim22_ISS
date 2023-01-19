package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoutes;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRouteRepository extends CrudRepository<FavoriteRoutes, Long> {

    List<FavoriteRoutes> findAll();
    Optional<FavoriteRoutes> findById(String Long);
    long count();
    FavoriteRoutes deleteById(String Long);
    boolean existsById(String Long);
}
