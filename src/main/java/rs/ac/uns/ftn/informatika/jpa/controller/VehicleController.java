package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestCurrentLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.model.CurrentLocation;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleService;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private IVehicleService vehicleService;

    private VehicleController(IVehicleService vehicleService){
        this.vehicleService = vehicleService;
    }


    @PutMapping(value = "/{id}/location", consumes = "application/json")
    public ResponseEntity<String> changeLocation(@PathVariable("id") String id, @RequestBody RequestCurrentLocationDTO requestCurrentLocationDTO) {


        Vehicle vehicleForUpdate = vehicleService.getVehicle(id).get();
        CurrentLocation updatedLocation = requestCurrentLocationDTO.parseToCurrentLocation();

        vehicleForUpdate.updateLocation(updatedLocation);
        vehicleService.add(vehicleForUpdate);
        return new ResponseEntity<>("Coordinates successfully updated", HttpStatus.NO_CONTENT);
    }


}
