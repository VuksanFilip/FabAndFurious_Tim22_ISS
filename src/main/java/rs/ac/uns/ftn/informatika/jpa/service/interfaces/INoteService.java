package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.model.Note;

import java.util.List;
import java.util.Optional;

public interface INoteService {

    List<Note> getAll();

    Optional<Note> getNote(String id);

    void add(Note note);

    Page<Note> findAll(Pageable page);

    Page<Note> getNotesByUserId(String userId, Pageable page);
}
