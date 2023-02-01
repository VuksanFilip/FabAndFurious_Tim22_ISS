package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.WorkingHour;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IWorkingHourService {

    List<WorkingHour> getAll();

    Optional<WorkingHour> getWorkHour(String id);

    void add(WorkingHour document);

    boolean checkIfShiftOngoing(String id);

    boolean checkIfPassed8hLimit(String id, LocalDateTime localDateTime);

    boolean checkIfShiftBetween(String id, LocalDateTime localDateTime);

    boolean checkIfEndIsNull(LocalDateTime localDateTime);

    void refreshUnfinishedShifts();
}
