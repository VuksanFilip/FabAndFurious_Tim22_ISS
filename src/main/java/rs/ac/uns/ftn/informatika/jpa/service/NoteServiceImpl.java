package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Note;
import rs.ac.uns.ftn.informatika.jpa.repository.NoteRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.INoteService;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements INoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAll() {
        return (List<Note>) this.noteRepository.findAll();
    }

    @Override
    public Optional<Note> getNote(String id) {
        return  this.noteRepository.findById(Long.parseLong(id));
    }

    public void add(Note note) {
        this.noteRepository.save(note);
    }
}
