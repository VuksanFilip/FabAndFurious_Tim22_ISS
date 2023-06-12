package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoutes;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    List<Passenger> findAll();
    Optional<Passenger> findById(String Long);
    Page<Passenger> findAll(Pageable pageable);
    Optional<Passenger> findByEmail(String email);
    boolean existsById(String Long);


    @Query("select p.favoriteRoutes from Passenger p where p.id = :passengerId")
    Page<FavoriteRoutes> findFavouriteRoutesByPassengerId(Long passengerId, Pageable page);
}
