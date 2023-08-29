package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestRideDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReportDayDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideNoStatusDTO;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.repository.RideRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RideServiceImpl implements IRideService {

    private final RideRepository rideRepository;
    private final IPassengerService passengerService;
    private final ILocationService locationService;
    private final IRouteService routeService;
    private final IDriverService driverService;
    private final IVehicleTypeService vehicleTypeService;

    @Autowired
    public RideServiceImpl(RideRepository rideRepository, IPassengerService passengerService, ILocationService locationService, IRouteService routeService, IDriverService driverService, IVehicleTypeService vehicleTypeService) {
        this.rideRepository = rideRepository;
        this.passengerService = passengerService;
        this.locationService = locationService;
        this.routeService = routeService;
        this.driverService = driverService;
        this.vehicleTypeService = vehicleTypeService;
    }

    public List<Ride> getAll() {
        return (List<Ride>) this.rideRepository.findAll();
    }

    @Override
    public Optional<Ride> getRide(String id) {
        return this.rideRepository.findById(Long.parseLong(id));
    }

    @Override
    public boolean existsById(String id) {
        return this.rideRepository.existsById(Long.parseLong(id));
    }

    @Override
    public Ride updateRide(Ride ride) {
        return this.rideRepository.save(ride);
    }

    public void add(Ride ride) {
        this.rideRepository.save(ride);
    }

    public long getSize() {
        return this.rideRepository.count();
    }

    public Page<Ride> findAll(Pageable page) {
        return rideRepository.findAll(page);
    }

    public List<Ride> findAllRidesByPassengerIdAndRideStatus(String passengerId, RideStatus rideStatus) {
        return this.rideRepository.findAllRidesByPassengerIdAndRideStatus(Long.parseLong(passengerId), rideStatus);
    }

    public boolean checkIfPassengerExistInRide(String rideId, String passengerId){
        if(this.rideRepository.findRideByRideIdAndPassengerId(Long.parseLong(rideId), Long.parseLong(passengerId)) != null){
            return true;
        }
        return false;
    }

    public boolean checkIfDriverExistInRide(String rideId, String driverId){
        if(this.rideRepository.findRideByRideIdAndDriverId(Long.parseLong(rideId), Long.parseLong(driverId)) != null){
            return true;
        }
        return false;
    }


    @Override
    public List<Ride> getRidesOfDriver(Driver driver) {
        List<Ride> allRides = new ArrayList<>();
        for (Ride r : getAll()) {
            if (r.getDriver() == driver) {
                allRides.add(r);
            }
        }
        return allRides;
    }

    public Ride getaActiveRideByDriverId(String id) {
        for (Ride ride : getAll()) {
            if ((ride.getDriver().getId() == Long.parseLong(id)) && (ride.getStatus() == RideStatus.STARTED)) {
                return ride;
            }
        }
        return null;
    }

    public Ride getActiveRideByPassengerId(String id) {
        for (Ride ride : getAll()) {
            for (Passenger passenger : ride.getPassengers()) {
                if (passenger.getId() == Long.parseLong(id) && (ride.getStatus() == RideStatus.STARTED)) {
                    return ride;
                }
            }
        }
        return null;
    }

    public void updateRideByStatus(String id, RideStatus status) {
        Optional<Ride> ride = getRide(id);
        ride.get().setStatus(status);
        this.rideRepository.save(ride.get());
    }

    public void updateRideByRejectionLetter(String id, RejectionLetter letter) {
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
            passengers.add(this.passengerService.findByEmail(p.getEmail()).get());
        }
        Ride newRide = new Ride(driver, passengers, routes, requestRideDTO.isBabyTransport(), requestRideDTO.isPetTransport(), requestRideDTO.getScheduledTime(), driver.getVehicle());
        add(newRide);
        driver.getRides().add(newRide);
        this.driverService.add(driver);

        return newRide;
    }

    public boolean checkIfNotPendingAndNotStartedById(String id) {
        Ride ride = getRide(id).get();
        if((ride.getStatus() != RideStatus.PENDING) && (ride.getStatus() != RideStatus.STARTED)){
            return true;
        }
        return false;
    }

    public boolean checkIfNotAcceptedById(String id) {
        Ride ride = getRide(id).get();
        if(ride.getStatus() != RideStatus.ACCEPTED) {
            return true;
        }
        return false;
    }

    public boolean checkIfNotPendingById(String id) {
        Ride ride = getRide(id).get();
        if (ride.getStatus() != RideStatus.PENDING) {
            return true;
        }
        return false;
    }

    public boolean checkIfNotStartedById(String id) {
        Ride ride = getRide(id).get();
        if (ride.getStatus() != RideStatus.STARTED) {
            return true;
        }
        return false;
    }

    public boolean checkIfNotPendingAndNotAcceptedById(String id) {
        Ride ride = getRide(id).get();
        if((ride.getStatus() != RideStatus.PENDING) && (ride.getStatus() != RideStatus.ACCEPTED)){
            return true;
        }
        return false;
    }

    public void updateRideByRejectionLetterAndStatus(String id, RejectionLetter rejectionLetter, RideStatus rideStatus){
        updateRideByRejectionLetter(id, rejectionLetter);
        updateRideByStatus(id, rideStatus);
    }

    public List<ResponseRideNoStatusDTO> getPageableResponseRide(Pageable page, String id){
        Page<Ride> rides = findAll(page);
        List<ResponseRideNoStatusDTO> responseRideDTOS = new ArrayList<>();
        for(Ride r: rides) {
            for (Passenger p : r.getPassengers()) {
                if (p.getId() == Long.parseLong(id)) {
                    responseRideDTOS.add(r.parseToResponseNoStatus());
                }
            }
        }
        return responseRideDTOS;
    }

    public List<ResponseRideNoStatusDTO> getResponseRide(String id){
        List<Ride> rides = getAll();
        List<ResponseRideNoStatusDTO> responseRideDTOS = new ArrayList<>();
        for(Ride r: rides) {
            for (Passenger p : r.getPassengers()) {
                if (p.getId() == Long.parseLong(id) && r.getStatus() == RideStatus.FINISHED) {
                    responseRideDTOS.add(r.parseToResponseNoStatus());
                }
            }
        }
        return responseRideDTOS;
    }

    public List<ResponseRideNoStatusDTO> getResponseRideDriver(String id){
        List<Ride> rides = getAll();
        List<ResponseRideNoStatusDTO> responseRideDTOS = new ArrayList<>();
        for(Ride r: rides) {
                if (r.getDriver().getId() == Long.parseLong("5") && r.getStatus() == RideStatus.FINISHED) {
                    responseRideDTOS.add(r.parseToResponseNoStatus());
                    System.out.println(responseRideDTOS);
                }

        }
        return responseRideDTOS;
    }

    public List<ResponseRideNoStatusDTO> getResponseRidePendingDriver(String id){
        List<Ride> rides = getAll();
        List<ResponseRideNoStatusDTO> responseRideDTOS = new ArrayList<>();
        for(Ride r: rides) {
            if (r.getDriver().getId() == Long.parseLong("5") && r.getStatus() == RideStatus.PENDING) {
                responseRideDTOS.add(r.parseToResponseNoStatus());
                System.out.println(responseRideDTOS);
            }

        }
        return responseRideDTOS;
    }

    public int getNumberOfRidesForPessanger(String id) {
        List<Ride> ridesForSize = getAll();
        int result = 0;
        for (Ride r : ridesForSize) {
            for (Passenger p : r.getPassengers()) {
                if (p.getId() == Long.parseLong(id)) {
                    result = result + 1;
                }
            }
        }
        return result;
    }

    public Page<Ride> getRidesForPassenger(String passengerId, Pageable page){
        return this.rideRepository.getRidesForPassenger(Long.parseLong(passengerId), page);
    }

    public Page<Ride> getRidesForDriver(String driverId, Pageable page){
        return this.rideRepository.getRidesForDriver(Long.parseLong(driverId), page);
    }

    public Page<Ride> findAll(String id, Pageable page, Date from, Date to) {

        if(from == null && to == null)
            return this.rideRepository.findAllByDriverId(Long.parseLong(id), page);
        if(to != null && from == null)
            return this.rideRepository.findAllByDriverIdAndTimeOfEndBefore(Long.parseLong(id), to, page);
        if(to == null)
            return this.rideRepository.findAllByDriverIdAndTimeOfStartAfter(Long.parseLong(id), from, page);

        return this.rideRepository.findAllByDriverIdAndTimeOfStartAfterAndTimeOfEndBefore(Long.parseLong(id), from, to, page);
    }

    @Override
    public List<Ride> getUserRidesBetweenDates(List<Ride> allRides, String from, String to) {
        List<Ride> allUserRidesBetweenDates = new ArrayList<>();

        for(Ride r : allRides){
            LocalDate endTime = r.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fromDate = LocalDate.parse(from, formatter);
            LocalDate toDate = LocalDate.parse(to, formatter);
            if(endTime.isAfter(fromDate) && endTime.isBefore(toDate)){
                allUserRidesBetweenDates.add(r);
            }
        }
        return allUserRidesBetweenDates;
    }

    @Override
    public List<ResponseReportDayDTO> countRidesForDay(List<Ride> rides, String from, String to) {
        List<LocalDate> allDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        while(!fromDate.isAfter(toDate)){
            allDates.add(fromDate);
            fromDate = fromDate.plusDays(1);
        }
        List<ResponseReportDayDTO> results = new ArrayList<>();
        for(LocalDate lc : allDates){
            int count = 0;
            for(Ride r : rides){
                if(r.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(lc)){
                    count++;
                }
            }
            ResponseReportDayDTO day = new ResponseReportDayDTO(lc, count);
            if(count != 0){
                results.add(day);
            }
        }
        return results;
    }

    @Override
    public List<ResponseReportDayDTO> countMoneyForDay(List<Ride> rides, String from, String to) {
        List<LocalDate> allDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        while(!fromDate.isAfter(toDate)){
            allDates.add(fromDate);
            fromDate = fromDate.plusDays(1);
        }
        List<ResponseReportDayDTO> results = new ArrayList<>();
        for(LocalDate lc : allDates){
            int count = 0;
            for(Ride r : rides){
                if(r.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(lc)){
                    count += r.getTotalCost();
                }
            }
            ResponseReportDayDTO day = new ResponseReportDayDTO(lc, count);
            if(count != 0){
                results.add(day);
            }
        }
        return results;
    }

    @Override
    public List<ResponseReportDayDTO> countKmsForDay(List<Ride> rides, String from, String to) {
        List<LocalDate> allDates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        while(!fromDate.isAfter(toDate)){
            allDates.add(fromDate);
            fromDate = fromDate.plusDays(1);
        }
        List<ResponseReportDayDTO> results = new ArrayList<>();
        for(LocalDate lc : allDates){
            int count = 0;
            for(Ride r : rides){
                if(r.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(lc)){
                    Route route = r.getRoutes().get(0);
                    count += this.vehicleTypeService.distance(route.getDeparture().getLatitude(), route.getDestination().getLatitude(), route.getDeparture().getLongitude(), route.getDestination().getLongitude());
                }
            }
            ResponseReportDayDTO day = new ResponseReportDayDTO(lc, count);
            if(count != 0){
                results.add(day);
            }
        }
        return results;
    }

    @Override
    public int getSumReport(List<ResponseReportDayDTO> dates) {
        int sum = 0;
        for(ResponseReportDayDTO d : dates){
            sum += d.getCount();
        }
        return sum;
    }

    @Override
    public float getAverageReport(List<ResponseReportDayDTO> dates) {
        return getSumReport(dates)/dates.size();
    }


}
