package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoutes;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.List;
import java.util.Optional;

public interface IPassengerService {

    List<Passenger> getAll();

    Optional<Passenger> getPassenger(String id);

    void add(Passenger passenger);

    Page<Passenger> findAll(Pageable page);

    Optional<Passenger> findByEmail(String email);

    List<Passenger> getAllWithStatusPending();

    boolean existsById(String id);

    boolean hasTenFavoriteRoutes(Passenger passenger);

    List<Passenger> getPassengersFromFavoriteRouteRequest(RequestFavoriteRouteDTO requestFavoriteRoute);

    boolean hasTenFavoriteRoutesForPassengers(List<Passenger> passengers);

    List<ResponsePassengerDTO> getAsPageableResponse(Pageable page);

    Page<FavoriteRoutes> findFavouriteRoutesByPassengerId(String passengerId, Pageable page);
}
