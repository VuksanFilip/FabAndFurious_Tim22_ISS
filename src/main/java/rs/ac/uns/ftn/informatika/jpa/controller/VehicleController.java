package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestCurrentLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.ILocationService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleService;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private IVehicleService vehicleService;
    private ILocationService locationService;

    public VehicleController(IVehicleService vehicleService, ILocationService locationService){
        this.vehicleService = vehicleService;
        this.locationService = locationService;
    }


    @PutMapping(value = "/{id}/location", consumes = "application/json")
//    @PreAuthorize("hasAnyRole('DRIVER')")
    public ResponseEntity<?> changeLocation(@PathVariable("id") String id, @RequestBody RequestCurrentLocationDTO requestCurrentLocationDTO) {
        if(!this.vehicleService.getVehicle(id).isPresent()){
            return new ResponseEntity<>("Vehicle does not exist!", HttpStatus.NOT_FOUND);
        }
        Location newLocation = new Location(requestCurrentLocationDTO.getAddress(), requestCurrentLocationDTO.getLatitude(), requestCurrentLocationDTO.getLongitude());
        this.locationService.add(newLocation);
        this.vehicleService.getVehicle(id).get().updateCurrentLocation(this.locationService.getLocationByAddress(newLocation.getAddress()));
        return new ResponseEntity<>("Coordinates successfully updated", HttpStatus.NO_CONTENT);
    }


}
