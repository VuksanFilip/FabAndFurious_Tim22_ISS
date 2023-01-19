package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.repository.LocationRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.ILocationService;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements ILocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAll() {
        return (List<Location>) this.locationRepository.findAll();
    }

    @Override
    public Optional<Location> getLocation(String id) {
        return  this.locationRepository.findById(Long.parseLong(id));
    }

    public void add(Location location) {
        this.locationRepository.save(location);
    }

    @Override
    public Location getLocationByAddress(String address) {
        return this.locationRepository.findByAddress(address);
    }

    public void addLocations(List<Location> locations){
        for(Location l: locations){
            add(l);
        }
    }
}
