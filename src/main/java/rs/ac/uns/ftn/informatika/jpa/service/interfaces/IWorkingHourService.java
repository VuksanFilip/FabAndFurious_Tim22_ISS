package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.WorkingHour;

import java.util.List;
import java.util.Optional;

public interface IWorkingHourService {

    List<WorkingHour> getAll();

    Optional<WorkingHour> getWorkHour(String id);

    void add(WorkingHour document);
}
