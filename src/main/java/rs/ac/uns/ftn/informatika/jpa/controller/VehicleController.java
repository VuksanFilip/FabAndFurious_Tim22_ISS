package rs.ac.uns.ftn.informatika.jpa.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestCurrentLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.ILocationService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private final IVehicleService vehicleService;
    private final ILocationService locationService;

    public VehicleController(IVehicleService vehicleService, ILocationService locationService){
        this.vehicleService = vehicleService;
        this.locationService = locationService;
    }

    @PutMapping(value = "/{id}/location", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('DRIVER')")
    public ResponseEntity<?> changeLocation(@PathVariable("id") String id, @Valid @RequestBody RequestCurrentLocationDTO requestCurrentLocationDTO) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.vehicleService.getVehicle(id).isPresent()){
            return new ResponseEntity<>(new MessageDTO("Vehicle does not exist!"), HttpStatus.NOT_FOUND);
        }

        Vehicle vehicle = vehicleService.getVehicle(id).get();
        Long locationId = vehicle.getCurrentLocation().getId();

        Location locationToUpdate = locationService.getLocation(locationId.toString()).get();
        locationToUpdate.update(requestCurrentLocationDTO);

        this.locationService.add(locationToUpdate);
        return new ResponseEntity<>(new MessageDTO("Coordinates successfully updated"), HttpStatus.NO_CONTENT);
    }
}
