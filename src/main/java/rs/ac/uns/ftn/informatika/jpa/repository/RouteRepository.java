package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.Route;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {

    List<Route> findAll();
    Optional<Route> findById(String Long);

}