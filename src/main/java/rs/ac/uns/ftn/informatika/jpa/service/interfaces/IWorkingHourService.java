package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverWorkingHourDTO;
import rs.ac.uns.ftn.informatika.jpa.model.WorkingHour;

import java.time.LocalDateTime;
import java.util.Date;
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

    Page<WorkingHour> findAll(Pageable page);

    List<ResponseDriverWorkingHourDTO> getPageableDriverWorkingHours(String id, Pageable page);

    Page<WorkingHour> findAll(String id, Pageable page, Date from, Date to);

    boolean checkIfShiftBetweenToGetDriver(String id, LocalDateTime localDateTime);
}
