package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.FavoriteLocations;

import java.util.List;
import java.util.Optional;

public interface IFavoriteLocationsService {

    List<FavoriteLocations> getAll();

    Optional<FavoriteLocations> getFavoriteLocations(String id);

    void add(FavoriteLocations favoriteLocations);

    long getSize();

    void deleteById(String id);

}
