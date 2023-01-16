package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Route;

import java.util.List;
import java.util.Optional;

public interface IPathService {

    List<Route> getAll();

    Optional<Route> getPath(String id);

    void add(Route route);
}
