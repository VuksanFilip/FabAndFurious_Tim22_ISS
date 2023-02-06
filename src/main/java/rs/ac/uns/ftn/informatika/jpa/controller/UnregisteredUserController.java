package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestAssumptionDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseAssumptionDTO;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleTypeService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/unregisteredUser")
public class UnregisteredUserController {

    private final IVehicleTypeService vehicleTypeService;

    public UnregisteredUserController(IVehicleTypeService vehicleTypeService){
        this.vehicleTypeService = vehicleTypeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAssumption(@Valid @RequestBody RequestAssumptionDTO requestAssumptionDTO) {

        int estimatedCost = vehicleTypeService.getEstimatedCost(requestAssumptionDTO);
        int estimatedTimeInMinutes = vehicleTypeService.getEstimatedTimeInMinutes(requestAssumptionDTO);

        return new ResponseEntity<>(new ResponseAssumptionDTO(estimatedTimeInMinutes, estimatedCost), HttpStatus.OK);
    }
}
