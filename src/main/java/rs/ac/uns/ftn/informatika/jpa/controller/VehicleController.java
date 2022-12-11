package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dummy.VehicleDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.model.Review;
import rs.ac.uns.ftn.informatika.jpa.dto.create.LocationDTO;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
    private VehicleDummy vehicleDummy = new VehicleDummy();

//    public void addVehicle(){
//        this.vehicleDummy.vehicles.put(Long.valueOf(1), new Vehicle(Long.valueOf(1), null, "model", null, "tablice", 4, null, false, false, new ArrayList<Review>()));
//    }


    @PutMapping(value = "/{id}/location", consumes = "application/json")
    public ResponseEntity<String> changeLocation(@PathVariable("id") Long id, @RequestBody LocationDTO locationDTO) {
        Vehicle vehicleForUpdate = vehicleDummy.vehicles.get(id);
        Location updatedLocation = locationDTO.parseToLocation();
        vehicleForUpdate.setLocation(updatedLocation);
        vehicleDummy.vehicles.put(vehicleForUpdate.getId(), vehicleForUpdate);
        return new ResponseEntity<String>("Coordinates successfully updated", HttpStatus.NO_CONTENT);
    }


}
