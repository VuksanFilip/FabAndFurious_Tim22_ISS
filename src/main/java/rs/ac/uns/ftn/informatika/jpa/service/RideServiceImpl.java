package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.repository.RideRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RideServiceImpl implements IRideService {

    private RideRepository rideRepository;

    private IPassengerService passengerService;
    private ILocationService locationService;
    private IRouteService routeService;
    private IDriverService driverService;

    @Autowired
    public RideServiceImpl(RideRepository rideRepository, IPassengerService passengerService, ILocationService locationService, IRouteService routeService, IDriverService driverService) {
        this.rideRepository = rideRepository;
        this.passengerService = passengerService;
        this.locationService = locationService;
        this.routeService = routeService;
        this.driverService = driverService;
    }

    public List<Ride> getAll() {
        return (List<Ride>) this.rideRepository.findAll();
    }

    @Override
    public Optional<Ride> getRide(String id) {
        return  this.rideRepository.findById(Long.parseLong(id));
    }

    @Override
    public boolean existsById(String id) {
        return  this.rideRepository.existsById(Long.parseLong(id));
    }

    public Page<Ride> findAll(Pageable page) {
        return rideRepository.findAll(page);
    }

    @Override
    public List<Ride> getRidesOfDriver(Driver driver) {
        List<Ride> allRides = new ArrayList<>();
        for(Ride r : getAll()){
            if(r.getDriver() == driver){
                allRides.add(r);
            }
        }
        return allRides;
    }

    @Override
    public Ride updateRide(Ride ride) {
        return  this.rideRepository.save(ride);
    }

    public void add(Ride ride) {
        this.rideRepository.save(ride);
    }

    public long getSize() { return this.rideRepository.count(); }

    public Ride getaActiveRideByDriverId(String id){
        for(Ride ride : getAll()){
            if((ride.getDriver().getId() == Long.parseLong(id)) && (ride.getStatus() == RideStatus.STARTED)){
                return ride;
            }
        }
        return null;
    }

    public Ride getActiveRideByPassengerId(String id){
        for(Ride ride : getAll()){
            for(Passenger passenger : ride.getPassengers()){
                if(passenger.getId() == Long.parseLong(id) && (ride.getStatus() == RideStatus.STARTED)){
                    return ride;
                }
            }
        }
        return null;
    }

    public void updateRideByStatus(String id, RideStatus status){
        Optional<Ride> ride = getRide(id);
        ride.get().setStatus(status);
        this.rideRepository.save(ride.get());
    }

    public void updateRideByRejectionLetter(String id, RejectionLetter letter){
        Optional<Ride> ride = getRide(id);
        ride.get().setLetter(letter);
        this.rideRepository.save(ride.get());
    }


    public boolean checkIfDriverHasPandingRides(Driver driver) {
        List<Ride> allRidesOfDriver = this.getRidesOfDriver(driver);
        for (Ride r : allRidesOfDriver) {
            if (r.getStatus() == RideStatus.PENDING) {
                return true;
            }
        }
        return false;
    }

    public Ride parseToRide(RequestRideDTO requestRideDTO, Driver driver) {
        List<Route> routes = new ArrayList<>();
        for(RequestLocationDTO l : requestRideDTO.getLocations()){
            Location l1 = new Location(l.getDeparture().getAddress(), l.getDeparture().getLatitude(), l.getDeparture().getLongitude());
            Location l2 = new Location(l.getDestination().getAddress(), l.getDestination().getLatitude(), l.getDestination().getLongitude());
            this.locationService.add(l1);
            this.locationService.add(l2);
            Route r = new Route(l1, l2);
            this.routeService.add(r);
            routes.add(r);
        }
        List<Passenger> passengers = new ArrayList<>();
        for(ResponsePassengerIdEmailDTO p : requestRideDTO.getPassengers()){
            passengers.add(this.passengerService.findByEmail(p.getEmail()));
        }
        Ride newRide = new Ride(driver, passengers, routes, requestRideDTO.isBabyTransport(), requestRideDTO.isPetTransport(), requestRideDTO.getScheduledTime());
        add(newRide);
        driver.getRides().add(newRide);
        this.driverService.add(driver);

        return newRide;
    }
}
