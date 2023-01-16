package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Route;
import rs.ac.uns.ftn.informatika.jpa.repository.PathRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPathService;

import java.util.List;
import java.util.Optional;

@Service
public class PathServiceImpl implements IPathService {

    private PathRepository pathRepository;

    @Autowired
    public PathServiceImpl(PathRepository pathRepository) {
        this.pathRepository = pathRepository;
    }

    public List<Route> getAll() {
        return (List<Route>) this.pathRepository.findAll();
    }

    @Override
    public Optional<Route> getPath(String id) {
        return  this.pathRepository.findById(Long.parseLong(id));
    }

    public void add(Route route) {
        this.pathRepository.save(route);
    }
}
