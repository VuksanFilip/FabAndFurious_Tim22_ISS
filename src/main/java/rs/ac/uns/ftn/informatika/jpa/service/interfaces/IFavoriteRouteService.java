package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoutes;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

import java.util.List;
import java.util.Optional;

public interface IFavoriteRouteService {

    List<FavoriteRoutes> getAll();

    Optional<FavoriteRoutes> getFavoriteLocations(String id);

    void add(FavoriteRoutes favoriteRoutes);

    long getSize();

    void deleteById(String id);

    boolean existsById(String id);
    FavoriteRoutes postFavoriteRoute(List<Passenger> passengers, RequestFavoriteRouteDTO requestFavoriteRoute);

    List<ResponseFavoriteRouteDTO> getResponseFavoriteRoutes(String passengerId);

    void deleteFavouriteRoutesFromPassengers(String id);
}
