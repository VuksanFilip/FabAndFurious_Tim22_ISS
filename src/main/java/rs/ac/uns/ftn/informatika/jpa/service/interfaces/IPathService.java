package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Path;

import java.util.List;
import java.util.Optional;

public interface IPathService {

    List<Path> getAll();

    Optional<Path> getPath(String id);

    void add(Path path);
}
