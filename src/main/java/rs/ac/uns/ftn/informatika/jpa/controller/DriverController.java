package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.*;
import rs.ac.uns.ftn.informatika.jpa.dummy.DocumentDummy;
import rs.ac.uns.ftn.informatika.jpa.dto.CreateDriverVehicleDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.DriverVehicleResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreateDriverDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreateDriverDocumentDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.DriverDocumentResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.DriverResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.DriverDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Document;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    private DriverDummy driverDummy = new DriverDummy();
    private DocumentDummy documentDummy = new DocumentDummy();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> createDriver(@RequestBody CreateDriverDTO driver) throws Exception {
        Long id = driverDummy.counter.incrementAndGet();
        DriverResponseDTO driverResponse = driver.parseToResponse(id);
        driverDummy.drivers.put(id, driver.parseToDriver(id));
        return new ResponseEntity<DriverResponseDTO>(driverResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> getDriver(@PathVariable("id") Long id) {
        Driver driver = driverDummy.drivers.get(id);
        if (driver == null) {
            return new ResponseEntity<DriverResponseDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DriverResponseDTO>(driver.parseToResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDocumentResponseDTO> createDriverDocument(@PathVariable("id") Long driverId, @RequestBody CreateDriverDocumentDTO document) throws Exception {
        Long id = documentDummy.counter.incrementAndGet();
        DriverDocumentResponseDTO driverDocumentResponse = document.parseToResponse(id,driverId);

//        for (Driver driver: driverDummy.drivers.values()){
//            if (driver.getId() == driverId){
//                driver.getDocuments().add(document.parseToDocument(id,driverId));
//            }
//        }
        documentDummy.documents.put(id,document.parseToDocument(id,driverId));

        return new ResponseEntity<DriverDocumentResponseDTO>(driverDocumentResponse, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverVehicleResponseDTO> createDriverVehicle(@PathVariable("id") Long driverId, @RequestBody CreateDriverVehicleDTO vehicle) throws Exception {
        Long id = driverDummy.counter.incrementAndGet();
        DriverVehicleResponseDTO driverVehicleResponse = vehicle.parseToResponse(id,driverId);

        return new ResponseEntity<DriverVehicleResponseDTO>(driverVehicleResponse, HttpStatus.CREATED);
    }


    @PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverWorkingHourResponseDTO> createDriverWorkingHour(@PathVariable("id") Long driverId,@RequestBody CreateDriverWorkingHourDTO workingHour) throws Exception {
        Long id = driverDummy.counter.incrementAndGet();
        DriverWorkingHourResponseDTO driverWorkingHourResponse = workingHour.parseToResponse(driverId);

        return new ResponseEntity<DriverWorkingHourResponseDTO>(driverWorkingHourResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverDocumentResponseDTO> getDriverDocuments(@PathVariable("id") Long driverId) {
        Document document = new Document();
        for (Document d:documentDummy.documents.values()){
            if (d.getDriver().getId() == driverId){
                document = d;
            }
        }
        if (document == null) {
            return new ResponseEntity<DriverDocumentResponseDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DriverDocumentResponseDTO>(document.parseToResponse(), HttpStatus.CREATED);
    }
}
