package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.VehicleType;

import java.util.List;
import java.util.Optional;

public interface VehicleTypeService {

    List<VehicleType> getAll();

    Optional<VehicleType> getVehicleType(String id);

    void add(VehicleType vehicleType);
}
