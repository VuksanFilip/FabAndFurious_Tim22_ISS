package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoutes;
import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Route;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;
import rs.ac.uns.ftn.informatika.jpa.repository.FavoriteRouteRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IFavoriteRouteService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.ILocationService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPassengerService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRouteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteRouteServiceImpl implements IFavoriteRouteService {

    private FavoriteRouteRepository favoriteRouteRepository;
    private IPassengerService passengerService;
    private ILocationService locationService;
    private IRouteService routeService;

    @Autowired
    public FavoriteRouteServiceImpl(FavoriteRouteRepository favoriteRouteRepository, IPassengerService passengerService, ILocationService locationService, IRouteService routeService) {
        this.favoriteRouteRepository = favoriteRouteRepository;
        this.passengerService = passengerService;
        this.locationService = locationService;
        this.routeService = routeService;
    }

    public long getSize() { return this.favoriteRouteRepository.count(); }


    public List<FavoriteRoutes> getAll() {
        return (List<FavoriteRoutes>) this.favoriteRouteRepository.findAll();
    }

    @Override
    public Optional<FavoriteRoutes> getFavoriteLocations(String id) {
        return  this.favoriteRouteRepository.findById(Long.parseLong(id));
    }

    public boolean existsById(String id) {
        return  this.favoriteRouteRepository.existsById(Long.parseLong(id));
    }


    public void add(FavoriteRoutes favoriteRoutes) {
        this.favoriteRouteRepository.save(favoriteRoutes);
    }

    @Override
    public void deleteById(String id) {
        this.favoriteRouteRepository.deleteById(Long.parseLong(id));
    }

    public FavoriteRoutes postFavoriteRoute(List<Passenger> passengers, RequestFavoriteRouteDTO requestFavoriteRoute){
        List<Route> routes = new ArrayList<>();
        for (RequestLocationDTO l : requestFavoriteRoute.getLocations()) {
            Location l1 = new Location(l.getDeparture().getAddress(), l.getDeparture().getLatitude(), l.getDeparture().getLongitude());
            Location l2 = new Location(l.getDestination().getAddress(), l.getDestination().getLatitude(), l.getDestination().getLongitude());
            this.locationService.add(l1);
            this.locationService.add(l2);
            Route r = new Route(l1, l2);
            this.routeService.add(r);
            routes.add(r);
        }
        List<Passenger> allPassengers = new ArrayList<>();
        for (ResponsePassengerIdEmailDTO p : requestFavoriteRoute.getPassengers()) {
            allPassengers.add(this.passengerService.findByEmail(p.getEmail()).get());
        }
        FavoriteRoutes favoriteRoutes = new FavoriteRoutes(requestFavoriteRoute.getFavoriteName(), routes, allPassengers, VehicleName.valueOf(requestFavoriteRoute.getVehicleType()), requestFavoriteRoute.isBabyTransport(), requestFavoriteRoute.isPetTransport());
        add(favoriteRoutes);
        for (ResponsePassengerIdEmailDTO p : requestFavoriteRoute.getPassengers()){
            Passenger passenger = this.passengerService.findByEmail(p.getEmail()).get();
            passenger.getFavoriteRoutes().add(favoriteRoutes);
            this.passengerService.add(passenger);
        }
        return favoriteRoutes;
    }

    public List<ResponseFavoriteRouteDTO> getResponseFavoriteRoutes(String passengerId) {
        List<FavoriteRoutes> favoriteRoutes = getAll();
        List<ResponseFavoriteRouteDTO> responseFavoriteRoutes = new ArrayList<>();
        for (FavoriteRoutes r : favoriteRoutes) {
            for(Passenger p : r.getPassengers()){
                if(p.getId() == Long.parseLong(passengerId)){
                    responseFavoriteRoutes.add(r.parseToResponse());
                }
            }
        }
        return responseFavoriteRoutes;
    }

    public void deleteFavouriteRoutesFromPassengers(String id){
    FavoriteRoutes favoriteRoutes = getFavoriteLocations(id).get();
        for(Passenger p : favoriteRoutes.getPassengers()) {
            Passenger passenger = this.passengerService.getPassenger(p.getId().toString()).get();
            passenger.getFavoriteRoutes().remove(favoriteRoutes);
            this.passengerService.add(passenger);
        }
    }
}
