package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestDriverVehicleDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverVehicleDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestDriverDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestDriverDocumentDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestDriverWorkingHourDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverDocumentDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverWorkingHourDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.DocumentDummy;
import rs.ac.uns.ftn.informatika.jpa.dummy.DriverDummy;
import rs.ac.uns.ftn.informatika.jpa.dummy.VehicleDummy;
import rs.ac.uns.ftn.informatika.jpa.dummy.WorkHourDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Document;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.model.WorkHour;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    private DriverDummy driverDummy = new DriverDummy();
    private DocumentDummy documentDummy = new DocumentDummy();
    private VehicleDummy vehicleDummy = new VehicleDummy();
    private WorkHourDummy workHourDummy = new WorkHourDummy();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverDTO> createDriver(@RequestBody RequestDriverDTO driver) throws Exception {
        Long id = driverDummy.counter.incrementAndGet();
        ResponseDriverDTO driverResponse = driver.parseToResponse(id);
        driverDummy.drivers.put(id, driver.parseToDriver(id));
        return new ResponseEntity<ResponseDriverDTO>(driverResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverDTO> getDriver(@PathVariable("id") Long id) {
        Driver driver = driverDummy.drivers.get(id);
        if (driver == null) {
            return new ResponseEntity<ResponseDriverDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ResponseDriverDTO>(driver.parseToResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverDocumentDTO> createDriverDocument(@PathVariable("id") Long driverId, @RequestBody RequestDriverDocumentDTO document) throws Exception {
        Long id = documentDummy.counter.incrementAndGet();
        ResponseDriverDocumentDTO driverDocumentResponse = document.parseToResponse(id,driverId);
        documentDummy.documents.put(id,document.parseToDocument(id,driverId));

        return new ResponseEntity<ResponseDriverDocumentDTO>(driverDocumentResponse, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverVehicleDTO> createDriverVehicle(@PathVariable("id") Long driverId, @RequestBody RequestDriverVehicleDTO vehicle) throws Exception {
        Long id = vehicleDummy.counter.incrementAndGet();
        ResponseDriverVehicleDTO driverVehicleResponse = vehicle.parseToResponse(id,driverId);
        vehicleDummy.vehicles.put(id,vehicle.parseToVehicle(id,driverId));


        return new ResponseEntity<ResponseDriverVehicleDTO>(driverVehicleResponse, HttpStatus.CREATED);
    }


    @PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverWorkingHourDTO> createDriverWorkingHour(@PathVariable("id") Long driverId, @RequestBody RequestDriverWorkingHourDTO workingHour) throws Exception {
        Long id = workHourDummy.counter.incrementAndGet();
        ResponseDriverWorkingHourDTO driverWorkingHourResponse = workingHour.parseToResponse(id);
        workHourDummy.workinghours.put(id,workingHour.parseToWorkHour(id));

        return new ResponseEntity<ResponseDriverWorkingHourDTO>(driverWorkingHourResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverDocumentDTO> getDriverDocuments(@PathVariable("id") Long driverId) {
        Document document = new Document();
        for (Document d:documentDummy.documents.values()){
            if (d.getDriver().getId() == driverId){
                document = d;
            }
        }
        if (document == null) {
            return new ResponseEntity<ResponseDriverDocumentDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ResponseDriverDocumentDTO>(document.parseToResponse(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverVehicleDTO> getDriverVehicle(@PathVariable("id") Long driverId) {
        Vehicle vehicle = new Vehicle();
        for (Vehicle v:vehicleDummy.vehicles.values()){
            if (v.getDriver().getId() == driverId){
                vehicle = v;
            }
        }
        if (vehicle == null) {
            return new ResponseEntity<ResponseDriverVehicleDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ResponseDriverVehicleDTO>(vehicle.parseToResponse(), HttpStatus.CREATED);
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

    @PutMapping (value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverDTO> updateDriver(@PathVariable("id") Long id, @RequestBody RequestDriverDTO driverDTO) {
        Driver driverForUpdate = driverDummy.drivers.get(id);
        Driver driver = driverDTO.parseToDriver(id);
        driverForUpdate.setFirstName(driver.getFirstName());
        driverForUpdate.setLastName(driver.getLastName());
        driverForUpdate.setPicture(driver.getPicture());
        driverForUpdate.setPhoneNumber(driver.getPhoneNumber());
        driverForUpdate.setEmail(driver.getEmail());
        driverForUpdate.setAddress(driver.getAddress());
        driverForUpdate.setPassword(driver.getPassword());
        driverDummy.drivers.put(id, driverForUpdate);
        return new ResponseEntity<ResponseDriverDTO>(driverForUpdate.parseToResponse(), HttpStatus.OK);
    }

    @PutMapping (value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDriverVehicleDTO> updateDriverVehicle(@PathVariable("id") Long driverId, @RequestBody RequestDriverVehicleDTO vehicleDTO) {

        Vehicle vehicleForUpdate = new Vehicle();
        for (Vehicle vehicle : vehicleDummy.vehicles.values()){
            if (vehicle.getDriver().getId() == driverId){
                vehicleForUpdate = vehicle;
            }
        }
        Vehicle vehicle = vehicleDTO.parseToVehicle(vehicleForUpdate.getId(),driverId);
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

    @DeleteMapping(value = "/document/{document-id}")
    public ResponseEntity<String> deleteDocument(@PathVariable("document-id") Long documentId) {
        documentDummy.documents.remove(documentId);
        return new ResponseEntity<>("Driver document deleted successfully", HttpStatus.NO_CONTENT);
    }

}
