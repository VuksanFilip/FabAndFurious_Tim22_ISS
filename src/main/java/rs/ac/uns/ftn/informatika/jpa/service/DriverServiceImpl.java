package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;
import rs.ac.uns.ftn.informatika.jpa.repository.DriverRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IDriverService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleTypeService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IWorkingHourService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements IDriverService {

    private DriverRepository driverRepository;
    private IWorkingHourService workingHourService;
    private IVehicleTypeService vehicleTypeService;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository, IWorkingHourService workingHourService, IVehicleTypeService vehicleTypeService) {
        this.driverRepository = driverRepository;
        this.workingHourService = workingHourService;
        this.vehicleTypeService = vehicleTypeService;
    }

    public List<Driver> getAll() {
        return (List<Driver>) this.driverRepository.findAll();
    }

    public Page<Driver> findAll(Pageable page) {
        return driverRepository.findAll(page);
    }

    @Override
    public Driver findByEmail(String email) {
        return driverRepository.findByEmail(email);
    }

    @Override
    public Optional<Driver> getDriver(String id) {
        return  this.driverRepository.findById(Long.parseLong(id));
    }

    public boolean existsById(String id) {
        return  this.driverRepository.existsById(Long.parseLong(id));
    }

    public void add(Driver driver) {
        this.driverRepository.save(driver);
    }

    public Driver getPerfectDriver(VehicleName name, Date date, RequestLocationDTO requestLocationDTO){

        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.of("GMT")).toLocalDateTime();
        List<Driver> drivers = getDriverByVehicleName(name);

        List<Driver> driverWithShift = new ArrayList<>();
        Driver perfectDriver = null;
        double perfectDistance = Double.POSITIVE_INFINITY;

        for(Driver driver : drivers) {
            if (workingHourService.checkIfShiftBetweenToGetDriver(driver.getId().toString(), localDateTime)) {
                driverWithShift.add(driver);
            }
        }

        for(Driver driver: driverWithShift){
            double distance = vehicleTypeService.distance(requestLocationDTO.getDeparture().getLatitude(),
                    driver.getVehicle().getCurrentLocation().getLatitude(),
                    requestLocationDTO.getDeparture().getLongitude(),
                    driver.getVehicle().getCurrentLocation().getLongitude());
            if(distance < perfectDistance){
                perfectDistance = distance;
                perfectDriver = driver;
            }
        }
        return perfectDriver;
    }



    public List<Driver> getDriverByVehicleName(VehicleName name){
        List<Driver> drivers = new ArrayList<>();
        for(Driver driver : getAll()){
            if(driver.getVehicle() != null && driver.getVehicle().getVehicleType().getVehicleName() == name){
                drivers.add(driver);
            }
        }
        return drivers;
    }
}
