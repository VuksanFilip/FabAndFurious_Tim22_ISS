package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.repository.DriverRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IDriverService;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements IDriverService {

    private DriverRepository driverRepository;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> getAll() {
        return (List<Driver>) this.driverRepository.findAll();
    }

    @Override
    public Optional<Driver> getDriver(String id) {
        return  this.driverRepository.findById(Long.parseLong(id));
    }

    public void add(Driver driver) {
        this.driverRepository.save(driver);
    }
}
