package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface IVehicleService {

    List<Vehicle> getAll();

    Optional<Vehicle> getVehicle(String id);

    void add(Vehicle getVehicle);


    void deleteById(Long id);
    boolean existsById(String id);

}
