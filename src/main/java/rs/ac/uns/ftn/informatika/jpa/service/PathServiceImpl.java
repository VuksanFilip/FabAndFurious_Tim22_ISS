package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Path;
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

    public List<Path> getAll() {
        return (List<Path>) this.pathRepository.findAll();
    }

    @Override
    public Optional<Path> getPath(String id) {
        return  this.pathRepository.findById(Long.parseLong(id));
    }

    public void add(Path path) {
        this.pathRepository.save(path);
    }
}
