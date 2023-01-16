package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IDocumentService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IDriverService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IWorkingHourService;

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


    public DriverController(IDriverService driverService, IDocumentService documentService, IVehicleService vehicleService, IWorkingHourService workHourService){
        this.driverService = driverService;
        this.documentService = documentService;
        this.vehicleService = vehicleService;
        this.workHourService = workHourService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDriver(@RequestBody RequestDriverDTO requestDriverDTO) throws Exception {
        if(this.driverService.findByEmail(requestDriverDTO.getEmail()) != null){
            return new ResponseEntity<>(new MessageDTO("User with that email already exists!"), HttpStatus.BAD_REQUEST);
        }
        Driver driver = requestDriverDTO.parseToDriver();
        driverService.add(driver);
        return new ResponseEntity<>(driver.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping
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
    public ResponseEntity<?> getDriver(@PathVariable("id") String id) {
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        Optional<Driver> driver = this.driverService.getDriver(id);
        return new ResponseEntity<>(driver.get().parseToResponse(), HttpStatus.OK);
    }

    @PutMapping (value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDriver(@PathVariable("id") String id, @RequestBody RequestDriverDTO requestDriverDTO) {
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
    public ResponseEntity<?> deleteDriverDocument(@PathVariable("document-id") String documentId) {
        if(!this.documentService.getDocument(documentId).isPresent()){
            return new ResponseEntity<>("Document does not exist", HttpStatus.NOT_FOUND);
        }
        documentService.deleteById(documentId);
        return new ResponseEntity<>("Driver document deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<?> addDriverVehicle(@PathVariable("id") String id, @RequestBody RequestDriverVehicleDTO requestDriverVehicleDTO) throws Exception {
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        Driver driver = this.driverService.getDriver(id).get();
        Vehicle vehicle = requestDriverVehicleDTO.parseToVehicle(driver);
        this.vehicleService.add(vehicle);
        driver.addVehicle(vehicle);
        this.driverService.add(driver);
        return new ResponseEntity<>(vehicle.parseToResponse(), HttpStatus.OK);
    }

    @PutMapping (value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeDriverVehicle(@PathVariable("id") String driverId, @RequestBody RequestDriverVehicleDTO requestDriverVehicleDTO) {
        if(!this.driverService.getDriver(driverId).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        Driver driver = this.driverService.getDriver(driverId).get();
        Vehicle currentVehicle = driver.getVehicle();
        Vehicle newVehicle = requestDriverVehicleDTO.parseToVehicle(driver);
        driver.addVehicle(newVehicle);
        this.vehicleService.add(newVehicle);
        this.driverService.add(driver);
        this.vehicleService.deleteById(currentVehicle.getId());
        return new ResponseEntity<>(newVehicle.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/working-hour", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDriverWorkingHours(@PathVariable("id") String driverId) {
        if(!this.driverService.getDriver(driverId).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<?> getDriverRides(@PathVariable("id") String driverId) {
        if(!this.driverService.getDriver(driverId).isPresent()){
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(value = "/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWorkingHour(@PathVariable("working-hour-id") Long workingHourId) {
        if(!this.workHourService.getWorkHour(workingHourId.toString()).isPresent()) {
            return new ResponseEntity<>("Working hour does not exist!", HttpStatus.NOT_FOUND);
        }
        WorkingHour workingHour = this.workHourService.getWorkHour(workingHourId.toString()).get();
        return new ResponseEntity<>(workingHour.parseToResponse(), HttpStatus.OK);
    }

    @PutMapping(value = "/working-hour/{working-hour-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateWorkingHour(@PathVariable("working-hour-id") Long workingHourId, @RequestBody RequestDriverWorkingHourEndDTO requestWorkingHour) {
        if(!this.workHourService.getWorkHour(workingHourId.toString()).isPresent()) {
            return new ResponseEntity<>("Working hour does not exist!", HttpStatus.NOT_FOUND);
        }
        WorkingHour workingHour = this.workHourService.getWorkHour(workingHourId.toString()).get();
        if(this.driverService.getDriver(workingHour.getDriver().getId().toString()).get().getVehicle() == null){
            return new ResponseEntity<>(new MessageDTO("Cannot end shift because the vehicle is not defined!"), HttpStatus.BAD_REQUEST);
        }
        WorkingHour updatedWorkingHour = new WorkingHour(workingHour.getId(), workingHour.getStart(), requestWorkingHour.getEnd(), workingHour.getDriver());
        this.workHourService.add(updatedWorkingHour);
        return new ResponseEntity<>(updatedWorkingHour.parseToResponse(), HttpStatus.OK);
    }
}
