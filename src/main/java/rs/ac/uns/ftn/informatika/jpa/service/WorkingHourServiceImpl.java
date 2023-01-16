package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.WorkingHour;
import rs.ac.uns.ftn.informatika.jpa.repository.WorkingHourRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IWorkingHourService;

import java.util.List;
import java.util.Optional;

@Service
public class WorkingHourServiceImpl implements IWorkingHourService {

    private WorkingHourRepository workingHourRepository;

    @Autowired
    public WorkingHourServiceImpl(WorkingHourRepository workingHourRepository) {
        this.workingHourRepository = workingHourRepository;
    }

    public List<WorkingHour> getAll() {
        return (List<WorkingHour>) this.workingHourRepository.findAll();
    }

    @Override
    public Optional<WorkingHour> getWorkHour(String id) {
        return  this.workingHourRepository.findById(Long.parseLong(id));
    }

    public void add(WorkingHour workingHour) {
        this.workingHourRepository.save(workingHour);
    }
}
