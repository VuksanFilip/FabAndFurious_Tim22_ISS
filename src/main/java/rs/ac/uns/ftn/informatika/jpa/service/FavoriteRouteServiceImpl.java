package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestFavoriteRouteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoute;
import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Route;
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


    public List<FavoriteRoute> getAll() {
        return (List<FavoriteRoute>) this.favoriteRouteRepository.findAll();
    }

    @Override
    public Optional<FavoriteRoute> getFavoriteLocations(String id) {
        return  this.favoriteRouteRepository.findById(Long.parseLong(id));
    }

    public boolean existsById(String id) {
        return  this.favoriteRouteRepository.existsById(Long.parseLong(id));
    }


    public void add(FavoriteRoute favoriteRoute) {
        this.favoriteRouteRepository.save(favoriteRoute);
    }

    @Override
    public void deleteById(String id) {
        this.favoriteRouteRepository.deleteById(Long.parseLong(id));
    }

    public FavoriteRoute postFavoriteRoute(Passenger passenger, RequestFavoriteRouteDTO requestFavoriteRoute){
        List<Route> routes = new ArrayList<>();
        for (RequestLocationDTO l : requestRideDTO.getLocations()) {
            Location l1 = new Location(l.getDeparture().getAddress(), l.getDeparture().getLatitude(), l.getDeparture().getLongitude());
            Location l2 = new Location(l.getDestination().getAddress(), l.getDestination().getLatitude(), l.getDestination().getLongitude());
            this.locationService.add(l1);
            this.locationService.add(l2);
            Route r = new Route(l1, l2);
            this.routeService.add(r);
            routes.add(r);
        }
        List<Passenger> passengers = new ArrayList<>();
        for (ResponsePassengerIdEmailDTO p : requestRideDTO.getPassengers()) {
            passengers.add(this.passengerService.findByEmail(p.getEmail()));
        }
        FavoriteRoute favoriteRoute = new FavoriteRoute(requestFavoriteRoute.getFavoriteName(), routes, passengers, requestFavoriteRoute.getVehicleType(), requestFavoriteRoute.isBabyTransport(), requestFavoriteRoute.isPetTransport());
        add(favoriteRoute);
        passenger.getFavoriteRoutes().add(favoriteRoute);
        this.passengerService.add(passenger);
        return favoriteRoute;
    }
}
