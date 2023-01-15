package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Driver> findAll(Pageable page) {
        return driverRepository.findAll(page);
    }

    @Override
    public Driver findByEmail(String email) {
        return driverRepository.findByEmail(email);
    }

    @Override
    public Optional<Driver> getDriver(String id) {
        return  this.driverRepository.findById(Long.parseLong(id));
    }

    public boolean existsById(String id) {
        return  this.driverRepository.existsById(Long.parseLong(id));
    }

    public void add(Driver driver) {
        this.driverRepository.save(driver);
    }
}
