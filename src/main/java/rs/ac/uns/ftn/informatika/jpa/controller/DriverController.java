package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.Message;
import rs.ac.uns.ftn.informatika.jpa.dto.request.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.dummy.DocumentDummy;
import rs.ac.uns.ftn.informatika.jpa.dummy.DriverDummy;
import rs.ac.uns.ftn.informatika.jpa.dummy.VehicleDummy;
import rs.ac.uns.ftn.informatika.jpa.dummy.WorkHourDummy;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IDocumentService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IDriverService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    private DriverDummy driverDummy = new DriverDummy();
    private DocumentDummy documentDummy = new DocumentDummy();
    private VehicleDummy vehicleDummy = new VehicleDummy();
    private WorkHourDummy workHourDummy = new WorkHourDummy();

    private IDriverService driverService;
    private IDocumentService documentService;
    private IVehicleService vehicleService;


    public DriverController(IDriverService driverService, IDocumentService documentService, IVehicleService vehicleService){
        this.driverService = driverService;
        this.documentService = documentService;
        this.vehicleService = vehicleService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDriver(@RequestBody RequestDriverDTO requestDriverDTO) throws Exception {
        if(this.driverService.findByEmail(requestDriverDTO.getEmail()) != null){
            return new ResponseEntity<>(new Message("User with that email already exists!"), HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(new Message("Driver does not exist!"), HttpStatus.NOT_FOUND);
        }
        Optional<Driver> driver = this.driverService.getDriver(id);
        return new ResponseEntity<>(driver.get().parseToResponse(), HttpStatus.OK);
    }

    @PutMapping (value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverDTO> update(@PathVariable("id") String id, @RequestBody RequestDriverDTO requestDriverDTO) {
        Driver driverForUpdate = driverService.getDriver(id).get();
        Driver driver = requestDriverDTO.parseToDriver();
        driverForUpdate.update(driver);
        driverService.add(driverForUpdate);
        return new ResponseEntity<>(driverForUpdate.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseDriverDocumentDTO>> getDriverDocuments(@PathVariable("id") String id) {
        Driver driver = driverService.getDriver(id).get();
        Document document = new Document();

        //MORA DOKUMENT DA IMA ID DRIVERA KAO I DRIVER ID DOKUMENTA(JEDAN BEZ DRUGOG NMG)
//        document.setDriver(new Driver(123L));
//        document.setId(1L);
//        driver.getDocuments().add(document);

        List<Document> driverDocuments = driver.getDocuments();
        List<ResponseDriverDocumentDTO> driverDocumentDTOS = new ArrayList<>();
        for(Document d: driverDocuments){
            driverDocumentDTOS.add(new ResponseDriverDocumentDTO(d));
        }

        return new ResponseEntity<>(driverDocumentDTOS, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverDocumentDTO> createDriverDocument(@PathVariable("id") String id, @RequestBody RequestDriverDocumentDTO requestDriverDocumentDTO) throws Exception {
        Driver driver = driverService.getDriver(id).get();
        Document document = requestDriverDocumentDTO.parseToDocument(driver);
        driver.getDocuments().add(document);
        documentService.add(document);
        driverService.add(driver);
        return new ResponseEntity<>(document.parseToResponse(), HttpStatus.CREATED);
    }

    //GRESKA SVE OBRISE(I DRIVERA I DOKUMENT)
    @DeleteMapping(value = "/document/{document-id}")
    public ResponseEntity<String> deleteDocument(@PathVariable("document-id") String documentId) {
        documentService.deleteById(documentId);
        return new ResponseEntity<>("Driver document deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverVehicleDTO> getDriverVehicle(@PathVariable("id") String id) {
        Vehicle vehicle = driverService.getDriver(id).get().getVehicle();
        return new ResponseEntity<>(vehicle.parseToResponse(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverVehicleDTO> createDriverVehicle(@PathVariable("id") String id, @RequestBody RequestDriverVehicleDTO requestDriverVehicleDTO) throws Exception {
        Driver driver = driverService.getDriver(id).get();
        Vehicle vehicle = requestDriverVehicleDTO.parseToVehicle(driver);
//        driver.setVehicle(vehicle);
//        vehicleService.add(vehicle);
//        driverService.add(driver);
        return new ResponseEntity<>(vehicle.parseToResponse(), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverWorkingHourDTO> createDriverWorkingHour(@PathVariable("id") Long driverId, @RequestBody RequestDriverWorkingHourDTO workingHour) throws Exception {
        Long id = workHourDummy.counter.incrementAndGet();
        ResponseDriverWorkingHourDTO driverWorkingHourResponse = workingHour.parseToResponse(id);
        workHourDummy.workinghours.put(id,workingHour.parseToWorkHour(id));

        return new ResponseEntity<ResponseDriverWorkingHourDTO>(driverWorkingHourResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverWorkingHourDTO> getDriverWorkingHour(@PathVariable("working-hour-id") Long workingHourId) {
        WorkHour workHour = new WorkHour();
        for (WorkHour w:workHourDummy.workinghours.values()){
            if (w.getId() == workingHourId){
                workHour = w;
            }
        }
        if (workHour == null) {
            return new ResponseEntity<ResponseDriverWorkingHourDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ResponseDriverWorkingHourDTO>(workHour.parseToResponse(), HttpStatus.CREATED);
    }

    @PutMapping (value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverVehicleDTO> updateDriverVehicle(@PathVariable("id") String driverId, @RequestBody RequestDriverVehicleDTO vehicleDTO) {

        Driver driver = driverService.getDriver(driverId).get();
        Vehicle vehicleForUpdate = new Vehicle();
        for (Vehicle vehicle : vehicleDummy.vehicles.values()){
            if (vehicle.getDriver().getId() == Long.parseLong(driverId)){
                vehicleForUpdate = vehicle;
            }
        }
        Vehicle vehicle = vehicleDTO.parseToVehicle(driver);
        vehicleForUpdate.setType(vehicle.getType());
        vehicleForUpdate.setVehicleModel(vehicle.getVehicleModel());
        vehicleForUpdate.setRegistarskeTablice(vehicle.getRegistarskeTablice());
        vehicleForUpdate.setLocation(vehicle.getLocation());
        vehicleForUpdate.setSeats(vehicle.getSeats());
        vehicleForUpdate.setBabyFriendly(vehicle.isBabyFriendly());
        vehicleForUpdate.setPetFriendly(vehicle.isPetFriendly());
        vehicleDummy.vehicles.put(vehicleForUpdate.getId(), vehicleForUpdate);

        return new ResponseEntity<ResponseDriverVehicleDTO>(vehicleForUpdate.parseToResponse(), HttpStatus.OK);
    }

    @PutMapping (value = "/working-hour/{working-hour-id} ", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverWorkingHourDTO> updateDriverWorkingHour(@PathVariable("working-hour-id") Long workingHourId, @RequestBody RequestDriverWorkingHourDTO workingHourDTO) {

        WorkHour workHourForUpdate = new WorkHour();
        for (WorkHour workHour : workHourDummy.workinghours.values()){
            if (workHour.getId() == workingHourId){
                workHourForUpdate = workHour;
            }
        }
        WorkHour workHour = workingHourDTO.parseToWorkHour(workHourForUpdate.getId());
        workHourForUpdate.setStart(workHour.getStart());
        workHourForUpdate.setEnd(workHour.getEnd());
        workHourDummy.workinghours.put(workHourForUpdate.getId(), workHourForUpdate);

        return new ResponseEntity<ResponseDriverWorkingHourDTO>(workHourForUpdate.parseToResponse(), HttpStatus.OK);
    }
}
