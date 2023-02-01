package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    private IDriverService driverService;
    private IDocumentService documentService;
    private IVehicleService vehicleService;
    private IWorkingHourService workHourService;
    private ILocationService locationService;
    private PasswordEncoder passwordEncoder;


    public DriverController(IDriverService driverService, IDocumentService documentService, IVehicleService vehicleService, IWorkingHourService workHourService, ILocationService locationService, PasswordEncoder passwordEncoder){
        this.driverService = driverService;
        this.documentService = documentService;
        this.vehicleService = vehicleService;
        this.workHourService = workHourService;
        this.locationService = locationService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> createDriver(@RequestBody RequestDriverDTO requestDriverDTO) throws Exception {
        if(this.driverService.findByEmail(requestDriverDTO.getEmail()) != null){
            return new ResponseEntity<>(new MessageDTO("User with that email already exists!"), HttpStatus.BAD_REQUEST);
        }
        Driver driver = requestDriverDTO.parseToDriver();
        driver.setPassword(passwordEncoder.encode(requestDriverDTO.getPassword()));
        driverService.add(driver);
        return new ResponseEntity<>(driver.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ResponsePageDTO> getDriversPage(Pageable page) {

        Page<Driver> drivers = driverService.findAll(page);
        int results = driverService.getAll().size();

        List<ResponseDriverDTO> responseDriverDTOS = new ArrayList<>();
        for (Driver d : drivers) {
            responseDriverDTOS.add(new ResponseDriverDTO(d));
        }
        return new ResponseEntity<>(new ResponsePageDTO(results, Arrays.asList(responseDriverDTOS.toArray())), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getDriver(@PathVariable("id") String id) {
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        Optional<Driver> driver = this.driverService.getDriver(id);
        return new ResponseEntity<>(driver.get().parseToResponse(), HttpStatus.OK);
    }

    @PutMapping (value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateDriver(@PathVariable("id") String id, @RequestBody RequestDriverWithoutPassDTO requestDriverDTO) {
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        Driver driverForUpdate = driverService.getDriver(id).get();
        Driver driver = requestDriverDTO.parseToDriver();
        driverForUpdate.update(driver);
        driverService.add(driverForUpdate);
        return new ResponseEntity<>(driverForUpdate.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getDriverDocuments(@PathVariable("id") String id) {
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        Driver driver = driverService.getDriver(id).get();
        List<Document> driverDocuments = driver.getDocuments();
        List<ResponseDriverDocumentDTO> driverDocumentDTOS = new ArrayList<>();
        for(Document d: driverDocuments){
            driverDocumentDTOS.add(new ResponseDriverDocumentDTO(d));
        }
        return new ResponseEntity<>(driverDocumentDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> addDriverDocument(@PathVariable("id") String id, @RequestBody RequestDriverDocumentDTO requestDriverDocumentDTO) throws Exception {
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        Driver driver = driverService.getDriver(id).get();
        Document document = requestDriverDocumentDTO.parseToDocument(driver);
        driver.getDocuments().add(document);
        documentService.add(document);
        driverService.add(driver);
        return new ResponseEntity<>(document.parseToResponse(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/document/{document-id}")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteDriverDocument(@PathVariable("document-id") String documentId) {
        if(!this.documentService.getDocument(documentId).isPresent()){
            return new ResponseEntity<>("Document does not exist", HttpStatus.NOT_FOUND);
        }
        documentService.deleteById(documentId);
        return new ResponseEntity<>("Driver document deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getDriverVehicle(@PathVariable("id") String id) {
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        if(this.driverService.getDriver(id).get().getVehicle() == null){
            return new ResponseEntity<>(new MessageDTO("Vehicle is not assigned!"), HttpStatus.BAD_REQUEST);
        }
        Vehicle vehicle = driverService.getDriver(id).get().getVehicle();
        return new ResponseEntity<>(vehicle.parseToResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> addDriverVehicle(@PathVariable("id") String id, @RequestBody RequestDriverVehicleDTO requestDriverVehicleDTO) throws Exception {
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        Driver driver = this.driverService.getDriver(id).get();
        Location currentLocation = new Location(requestDriverVehicleDTO.getCurrentLocation().getAddress(), requestDriverVehicleDTO.getCurrentLocation().getLatitude(), requestDriverVehicleDTO.getCurrentLocation().getLongitude());
        this.locationService.add(currentLocation);
        Vehicle vehicle = requestDriverVehicleDTO.parseToVehicle(driver, currentLocation);
        this.vehicleService.add(vehicle);
        driver.addVehicle(vehicle);
        this.driverService.add(driver);
        return new ResponseEntity<>(vehicle.parseToResponse(), HttpStatus.OK);
    }

    @PutMapping (value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> changeDriverVehicle(@PathVariable("id") String driverId, @RequestBody RequestDriverVehicleDTO requestDriverVehicleDTO) {
        if(!this.driverService.getDriver(driverId).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        Driver driver = this.driverService.getDriver(driverId).get();
        Vehicle currentVehicle = driver.getVehicle();
        Location currentLocation = new Location(requestDriverVehicleDTO.getCurrentLocation().getAddress(), requestDriverVehicleDTO.getCurrentLocation().getLatitude(), requestDriverVehicleDTO.getCurrentLocation().getLongitude());
        Vehicle newVehicle = requestDriverVehicleDTO.parseToVehicle(driver, currentLocation);
        driver.addVehicle(newVehicle);
        this.vehicleService.add(newVehicle);
        this.driverService.add(driver);
        currentVehicle.setDriver(null);
        this.vehicleService.add(currentVehicle);
        return new ResponseEntity<>(newVehicle.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/working-hour", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getDriverWorkingHours(@PathVariable("id") String driverId) {
        if(!this.driverService.getDriver(driverId).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('DRIVER')")
    public ResponseEntity<?> createDriverWorkingHour(@PathVariable("id") String driverId, @RequestBody RequestDriverWorkingHourStartDTO requestWorkingHour) throws Exception {
        if(!this.driverService.getDriver(driverId).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        if(this.driverService.getDriver(driverId).get().getVehicle() == null){
            return new ResponseEntity<>(new MessageDTO("Cannot start shift because the vehicle is not defined!"), HttpStatus.BAD_REQUEST);
        }
        Driver driver = this.driverService.getDriver(driverId).get();
        WorkingHour workingHour = new WorkingHour(driver, requestWorkingHour);
        this.workHourService.add(workingHour);
        return new ResponseEntity<>(workingHour.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getDriverRides(@PathVariable("id") String driverId) {
        if(!this.driverService.getDriver(driverId).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(value = "/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getWorkingHour(@PathVariable("working-hour-id") Long workingHourId) {
        if(!this.workHourService.getWorkHour(workingHourId.toString()).isPresent()) {
            return new ResponseEntity<>("Working hour does not exist!", HttpStatus.NOT_FOUND);
        }
        WorkingHour workingHour = this.workHourService.getWorkHour(workingHourId.toString()).get();
        return new ResponseEntity<>(workingHour.parseToResponse(), HttpStatus.OK);
    }

    @PutMapping(value = "/working-hour/{working-hour-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('DRIVER')")
    public ResponseEntity<?> updateWorkingHour(@PathVariable("working-hour-id") Long workingHourId, @RequestBody RequestDriverWorkingHourEndDTO requestWorkingHour) {
        if(!this.workHourService.getWorkHour(workingHourId.toString()).isPresent()) {
            return new ResponseEntity<>("Working hour does not exist!", HttpStatus.NOT_FOUND);
        }
        WorkingHour workingHour = this.workHourService.getWorkHour(workingHourId.toString()).get();
        if(this.driverService.getDriver(workingHour.getDriver().getId().toString()).get().getVehicle() == null){
            return new ResponseEntity<>(new MessageDTO("Cannot endTime shift because the vehicle is not defined!"), HttpStatus.BAD_REQUEST);
        }
        WorkingHour updatedWorkingHour = new WorkingHour(workingHour.getId(), workingHour.getStart(), requestWorkingHour.getEnd(), workingHour.getDriver());
        this.workHourService.add(updatedWorkingHour);
        return new ResponseEntity<>(updatedWorkingHour.parseToResponse(), HttpStatus.OK);
    }
}
