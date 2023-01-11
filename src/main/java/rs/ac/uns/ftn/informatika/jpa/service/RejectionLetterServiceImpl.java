package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.RejectionLetter;
import rs.ac.uns.ftn.informatika.jpa.repository.RejectionLetterRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRejectionLetterService;

import java.util.List;
import java.util.Optional;

@Service
public class RejectionLetterServiceImpl implements IRejectionLetterService {

    private RejectionLetterRepository rejectionLetterRepository;

    @Autowired
    public RejectionLetterServiceImpl(RejectionLetterRepository rejectionLetterRepository) {
        this.rejectionLetterRepository = rejectionLetterRepository;
    }

    public List<RejectionLetter> getAll() {
        return (List<RejectionLetter>) this.rejectionLetterRepository.findAll();
    }

    @Override
    public Optional<RejectionLetter> getRejectionLetter(String id) {
        return  this.rejectionLetterRepository.findById(Long.parseLong(id));
    }

    public void add(RejectionLetter rejectionLetter) {
        this.rejectionLetterRepository.save(rejectionLetter);
    }
}
