package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoute;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.List;
import java.util.Optional;

public interface IFavoriteRouteService {

    List<FavoriteRoute> getAll();

    Optional<FavoriteRoute> getFavoriteLocations(String id);

    void add(FavoriteRoute favoriteRoute);

    long getSize();

    void deleteById(String id);

    boolean existsById(String id);
    FavoriteRoute postFavoriteRoute(Passenger passenger, RequestFavoriteRouteDTO requestFavoriteRoute);
}
