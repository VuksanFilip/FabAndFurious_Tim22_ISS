package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverWorkingHourDTO;
import rs.ac.uns.ftn.informatika.jpa.model.WorkingHour;
import rs.ac.uns.ftn.informatika.jpa.repository.WorkingHourRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IWorkingHourService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkingHourServiceImpl implements IWorkingHourService {

    private final int limit = 8;
    private WorkingHourRepository workingHourRepository;

    @Autowired
    public WorkingHourServiceImpl(WorkingHourRepository workingHourRepository) {
        this.workingHourRepository = workingHourRepository;
    }

    public List<WorkingHour> getAll() {
        return (List<WorkingHour>) this.workingHourRepository.findAll();
    }

    public Page<WorkingHour> findAll(Pageable page) {
        return workingHourRepository.findAll(page);
    }

    public List<ResponseDriverWorkingHourDTO> getPageableDriverWorkingHours(String id, Pageable page){
        Page<WorkingHour> workingHours = findAll(page);
        List<ResponseDriverWorkingHourDTO> workingHoursResponse = new ArrayList<>();
        for(WorkingHour w: workingHours) {
            if (w.getDriver().getId() == Long.parseLong(id)) {
                workingHoursResponse.add(w.parseToResponse());
            }
        }
        return workingHoursResponse;
    }

    @Override
    public Optional<WorkingHour> getWorkHour(String id) {
        return this.workingHourRepository.findById(Long.parseLong(id));
    }

    public void add(WorkingHour workingHour) {
        this.workingHourRepository.save(workingHour);
    }

    public List<WorkingHour> getAllByDriverId(String id) {
        List<WorkingHour> driverWorkingHours = new ArrayList<>();
        for (WorkingHour workingHour : getAll()) {
            if (workingHour.getDriver().getId() == Long.parseLong(id)) {
                driverWorkingHours.add(workingHour);
            }
        }
        return driverWorkingHours;
    }

    public boolean checkIfShiftOngoing(String id) {
        for (WorkingHour workingHour : getAllByDriverId(id))
            if (workingHour.getEndTime() == null) {
                return true;
            }
        return false;
    }

    public List<WorkingHour> getWorkingHoursIn24h(List<WorkingHour> workingHours, LocalDateTime localDateTime) {
        List<WorkingHour> workingHours24h = new ArrayList<>();
        for (WorkingHour workingHour : workingHours) {
            if (workingHour.getStart().isAfter(localDateTime.minusHours(24))) {
                workingHours24h.add(workingHour);
            }
        }
        return workingHours24h;
    }

    public boolean checkIfPassed8hLimit(String id, LocalDateTime localDateTime) {
        List<WorkingHour> workingHours24 = getWorkingHoursIn24h(getAllByDriverId(id), localDateTime);
        long minutes = 0;
        for (WorkingHour workingHour24 : workingHours24) {
            long number = workingHour24.getStart().until(workingHour24.getEndTime(), ChronoUnit.MINUTES);
            System.out.println(number);
            minutes += number;
            if (minutes > 60 * limit) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfShiftBetween(String id, LocalDateTime localDateTime) {
        List<WorkingHour> workingHours24 = getWorkingHoursIn24h(getAllByDriverId(id), localDateTime);
        for (WorkingHour workingHour24 : workingHours24) {
            if ((workingHour24.getStart().isBefore(localDateTime)) && (workingHour24.getEndTime().isAfter(localDateTime))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfEndIsNull(LocalDateTime localDateTime){
        if(localDateTime == null){
            return true;
        }
        return false;
    }

    public void refreshUnfinishedShifts(){
        for(WorkingHour workingHour : getAll()){
            int minutes = getWorkTimeIn24ForDriver(workingHour.getDriver().getId().toString(), workingHour.getStart());
            if(workingHour.getEndTime() == null && workingHour.getStart().plusHours(8).minusMinutes(minutes).isBefore(LocalDateTime.now())){
                workingHour.setEndTime(workingHour.getStart().plusHours(8).minusMinutes(minutes));
                add(workingHour);
            }
        }
    }

    public int getWorkTimeIn24ForDriver(String id, LocalDateTime localDateTime) {
        List<WorkingHour> workingHours24 = getWorkingHoursIn24h(getAllByDriverId(id), localDateTime);
        long minutes = 0;
        for (WorkingHour workingHour24 : workingHours24) {
            long number = workingHour24.getStart().until(workingHour24.getEndTime(), ChronoUnit.MINUTES);
            minutes += number;
        }
        return (int) minutes;
    }
}

