package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestAssumptionDTO;
import rs.ac.uns.ftn.informatika.jpa.model.VehicleType;

import java.util.List;
import java.util.Optional;

public interface IVehicleTypeService {

    List<VehicleType> getAll();

    Optional<VehicleType> getVehicleType(String id);

    void add(VehicleType vehicleType);

    int getEstimatedCost(RequestAssumptionDTO requestAssumptionDTO);

    int getEstimatedTimeInMinutes(RequestAssumptionDTO requestAssumptionDTO);

    double distance(double lat1, double lat2, double lon1, double lon2);
}
