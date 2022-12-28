package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Panic;

import java.util.List;
import java.util.Optional;

public interface PanicService {

    List<Panic> getAll();

    Optional<Panic> getPanic(String id);

    void add(Panic panic);
}
