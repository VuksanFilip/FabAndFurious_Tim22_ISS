package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.CreateDriverDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CreatePassengerDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.DriverResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.DriverDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    private DriverDummy driverDummy = new DriverDummy();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> createPassenger(@RequestBody CreateDriverDTO driver) throws Exception {
        Long id = driverDummy.counter.incrementAndGet();
        DriverResponseDTO driverResponse = driver.parseToResponse(id);
        driverDummy.drivers.put(id, driver.parseToDriver(id));
        return new ResponseEntity<DriverResponseDTO>(driverResponse, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverResponseDTO> getPassenger(@PathVariable("id") Long id) {
        Driver driver = driverDummy.drivers.get(id);
        if (driver == null) {
            return new ResponseEntity<DriverResponseDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<DriverResponseDTO>(driver.parseToResponse(), HttpStatus.OK);
    }
}
