package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;

import java.util.List;
import java.util.Optional;

public interface IDriverService {

    List<Driver> getAll();

    Optional<Driver> getDriver(String id);

    void add(Driver driver);

    Page<Driver> findAll(Pageable page);

    Driver findByEmail(String email);

    boolean existsById(String id);
}
