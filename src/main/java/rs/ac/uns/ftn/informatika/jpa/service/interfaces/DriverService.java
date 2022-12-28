package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {

    List<Driver> getAll();

    Optional<Driver> getDriver(String id);

    void add(Driver driver);
}
