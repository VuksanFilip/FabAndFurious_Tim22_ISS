package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.WorkHour;
import rs.ac.uns.ftn.informatika.jpa.repository.WorkHourRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IWorkHourService;

import java.util.List;
import java.util.Optional;

@Service
public class WorkHourServiceImpl implements IWorkHourService {

    private WorkHourRepository workHourRepository;

    @Autowired
    public WorkHourServiceImpl(WorkHourRepository workHourRepository) {
        this.workHourRepository = workHourRepository;
    }

    public List<WorkHour> getAll() {
        return (List<WorkHour>) this.workHourRepository.findAll();
    }

    @Override
    public Optional<WorkHour> getWorkHour(String id) {
        return  this.workHourRepository.findById(Long.parseLong(id));
    }

    public void add(WorkHour workHour) {
        this.workHourRepository.save(workHour);
    }
}
