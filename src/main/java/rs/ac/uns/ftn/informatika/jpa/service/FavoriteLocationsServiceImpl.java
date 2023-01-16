package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoute;
import rs.ac.uns.ftn.informatika.jpa.repository.FavoriteLocationsRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IFavoriteLocationsService;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteLocationsServiceImpl implements IFavoriteLocationsService {

    private FavoriteLocationsRepository favoriteLocationsRepository;

    @Autowired
    public FavoriteLocationsServiceImpl(FavoriteLocationsRepository favoriteLocationsRepository) {
        this.favoriteLocationsRepository = favoriteLocationsRepository;
    }

    public long getSize() { return this.favoriteLocationsRepository.count(); }


    public List<FavoriteRoute> getAll() {
        return (List<FavoriteRoute>) this.favoriteLocationsRepository.findAll();
    }

    @Override
    public Optional<FavoriteRoute> getFavoriteLocations(String id) {
        return  this.favoriteLocationsRepository.findById(Long.parseLong(id));
    }

    public boolean existsById(String id) {
        return  this.favoriteLocationsRepository.existsById(Long.parseLong(id));
    }


    public void add(FavoriteRoute favoriteRoute) {
        this.favoriteLocationsRepository.save(favoriteRoute);
    }

    @Override
    public void deleteById(String id) {
        this.favoriteLocationsRepository.deleteById(Long.parseLong(id));
    }
}
