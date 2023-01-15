package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.repository.VehicleRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleService;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements IVehicleService {

    private VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAll() {
        return (List<Vehicle>) this.vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> getVehicle(String id) {
        return  this.vehicleRepository.findById(Long.parseLong(id));
    }

    public boolean existsById(String id) {
        return  this.vehicleRepository.existsById(Long.parseLong(id));
    }

    public void add(Vehicle vehicle) {
        this.vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteById(Long id) {
        this.vehicleRepository.deleteById(id);
    }
}
