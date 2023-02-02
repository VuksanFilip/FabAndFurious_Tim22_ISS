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
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.ILocationService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleService;

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
    @PreAuthorize("hasAnyRole('DRIVER')")
    public ResponseEntity<?> changeLocation(@PathVariable("id") String id, @RequestBody RequestCurrentLocationDTO requestCurrentLocationDTO) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.vehicleService.getVehicle(id).isPresent()){
            return new ResponseEntity<>("Vehicle does not exist!", HttpStatus.NOT_FOUND);
        }

        Location newLocation = new Location(requestCurrentLocationDTO.getAddress(), requestCurrentLocationDTO.getLatitude(), requestCurrentLocationDTO.getLongitude());
        this.locationService.add(newLocation);

        this.vehicleService.getVehicle(id).get().updateCurrentLocation(this.locationService.getLocationByAddress(newLocation.getAddress()));
        return new ResponseEntity<>("Coordinates successfully updated", HttpStatus.NO_CONTENT);
    }


}
