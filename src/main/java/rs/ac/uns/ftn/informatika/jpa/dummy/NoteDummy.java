package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.dto.create.CreateNoteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.NoteResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Note;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class NoteDummy {

    public static AtomicLong counter;
    public ConcurrentHashMap<Long, Note> notes;

    public NoteDummy() {
        this.counter = new AtomicLong();
        this.notes = new ConcurrentHashMap<Long, Note>();
    }

    public NoteResponseDTO parseToResponse(CreateNoteDTO createNoteDTO){
        return new NoteResponseDTO(Long.valueOf(this.notes.size()+1), new Date(), createNoteDTO.getMessage());
    }
}
