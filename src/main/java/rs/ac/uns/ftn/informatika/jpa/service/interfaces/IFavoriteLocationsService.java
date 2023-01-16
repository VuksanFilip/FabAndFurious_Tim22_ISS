package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoute;

import java.util.List;
import java.util.Optional;

public interface IFavoriteLocationsService {

    List<FavoriteRoute> getAll();

    Optional<FavoriteRoute> getFavoriteLocations(String id);

    void add(FavoriteRoute favoriteRoute);

    long getSize();

    void deleteById(String id);

    boolean existsById(String id);
}
