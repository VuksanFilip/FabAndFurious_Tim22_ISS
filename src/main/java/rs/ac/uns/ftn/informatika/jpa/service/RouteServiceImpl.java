package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Route;
import rs.ac.uns.ftn.informatika.jpa.repository.RouteRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRouteService;

import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements IRouteService {

    private RouteRepository routeRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getAll() {
        return (List<Route>) this.routeRepository.findAll();
    }

    @Override
    public Optional<Route> getPath(String id) {
        return  this.routeRepository.findById(Long.parseLong(id));
    }

    public void add(Route route) {
        this.routeRepository.save(route);
    }
}
