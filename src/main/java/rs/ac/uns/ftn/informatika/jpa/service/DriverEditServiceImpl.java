package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.DriverEdit;
import rs.ac.uns.ftn.informatika.jpa.repository.DriverEditRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IDriverEditService;

@Service
public class DriverEditServiceImpl implements IDriverEditService {

    private DriverEditRepository driverEditRepository;

    @Autowired
    public DriverEditServiceImpl(DriverEditRepository driverEditRepository){
        this.driverEditRepository = driverEditRepository;
    }

    @Override
    public void add(DriverEdit driverEdit) {
        this.driverEditRepository.save(driverEdit);
    }

    @Override
    public void delete(DriverEdit edit) {
        this.driverEditRepository.delete(edit);
    }
}
