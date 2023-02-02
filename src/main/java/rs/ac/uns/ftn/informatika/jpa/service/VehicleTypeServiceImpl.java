package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestAssumptionDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationAssumptionDTO;
import rs.ac.uns.ftn.informatika.jpa.model.VehicleType;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;
import rs.ac.uns.ftn.informatika.jpa.repository.VehicleTypeRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IVehicleTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleTypeServiceImpl implements IVehicleTypeService {

    private final int kmPerHour = 50;
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

    public int getEstimatedCost(RequestAssumptionDTO requestAssumptionDTO){
        double priceByVehicleType = getPriceByVehicleType(requestAssumptionDTO.getVehicleType());
        double km = calculateDistance(requestAssumptionDTO.getLocationDTOS());
        return (int) (priceByVehicleType + (km * 120));
    }

    public int getEstimatedTimeInMinutes(RequestAssumptionDTO requestAssumptionDTO){
        return (int) ((calculateDistance(requestAssumptionDTO.getLocationDTOS()) / kmPerHour) * 60);
    }

    public double getPriceByVehicleType(VehicleName name){
        List<VehicleType> vehiclesType = getAll();
        for(VehicleType vehicleType : vehiclesType){
            if(vehicleType.getVehicleName().equals(name)) {
                return vehicleType.getPricePerKm();
            }
        }
        return 0.0;
    }

    public double calculateDistance(List<RequestLocationAssumptionDTO> paths){
        double totalKm = 0;
        for(RequestLocationAssumptionDTO path : paths){
            double distance = distance(path.getDeparture().getLatitude(),
                    path.getDestination().getLatitude(),
                    path.getDeparture().getLongitude(),
                    path.getDestination().getLongitude());

            totalKm = totalKm + distance;
        }
        System.out.println(totalKm);
        return totalKm;
    }

    public double distance(double lat1, double lat2, double lon1, double lon2) {

        final int radius = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = radius * c * 1000; // convert to meters
        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }
}
