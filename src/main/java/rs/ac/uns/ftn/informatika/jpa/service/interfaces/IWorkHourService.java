package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.WorkHour;

import java.util.List;
import java.util.Optional;

public interface IWorkHourService {

    List<WorkHour> getAll();

    Optional<WorkHour> getWorkHour(String id);

    void add(WorkHour document);
}
