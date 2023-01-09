package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteLocations;
import rs.ac.uns.ftn.informatika.jpa.repository.FavoriteLocationsRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.FavoriteLocationsService;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteLocationsServiceImpl implements FavoriteLocationsService {

    private FavoriteLocationsRepository favoriteLocationsRepository;

    @Autowired
    public FavoriteLocationsServiceImpl(FavoriteLocationsRepository favoriteLocationsRepository) {
        this.favoriteLocationsRepository = favoriteLocationsRepository;
    }

    public long getSize() { return this.favoriteLocationsRepository.count(); }


    public List<FavoriteLocations> getAll() {
        return (List<FavoriteLocations>) this.favoriteLocationsRepository.findAll();
    }

    @Override
    public Optional<FavoriteLocations> getFavoriteLocations(String id) {
        return  this.favoriteLocationsRepository.findById(Long.parseLong(id));
    }

    public FavoriteLocations add(FavoriteLocations favoriteLocations) {
        return this.favoriteLocationsRepository.save(favoriteLocations);
    }

    @Override
    public void deleteById(String id) {
        this.favoriteLocationsRepository.deleteById(Long.parseLong(id));
    }
}
