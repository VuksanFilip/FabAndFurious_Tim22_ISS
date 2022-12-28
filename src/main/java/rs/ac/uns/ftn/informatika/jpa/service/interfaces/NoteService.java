package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    List<Note> getAll();

    Optional<Note> getNote(String id);

    void add(Note note);
}
