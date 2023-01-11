package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.RejectionLetter;

import java.util.List;
import java.util.Optional;

public interface IRejectionLetterService {

    List<RejectionLetter> getAll();

    Optional<RejectionLetter> getRejectionLetter(String id);

    void add(RejectionLetter rejectionLetter);
}
