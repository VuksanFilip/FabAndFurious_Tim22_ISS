package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.VehicleType;
import rs.ac.uns.ftn.informatika.jpa.repository.VehicleTypeRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.VehicleTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {

    private VehicleTypeRepository vehicleTypeRepository;

    @Autowired
    public VehicleTypeServiceImpl(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public List<VehicleType> getAll() {
        return (List<VehicleType>) this.vehicleTypeRepository.findAll();
    }

    @Override
    public Optional<VehicleType> getVehicleType(String id) {
        return  this.vehicleTypeRepository.findById(Long.parseLong(id));
    }

    public void add(VehicleType vehicleType) {
        this.vehicleTypeRepository.save(vehicleType);
    }
}
