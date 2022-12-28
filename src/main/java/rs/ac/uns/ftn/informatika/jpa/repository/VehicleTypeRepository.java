package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.VehicleType;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleTypeRepository extends CrudRepository<VehicleType, Long> {

    List<VehicleType> findAll();
    Optional<VehicleType> findById(String Long);

}