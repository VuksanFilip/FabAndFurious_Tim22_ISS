package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestNoteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseNoteDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Note;

import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class NoteDummy {

    public static AtomicLong counter;
    public ConcurrentHashMap<Long, Note> notes;

    public NoteDummy() {
        this.counter = new AtomicLong();
        this.notes = new ConcurrentHashMap<Long, Note>();
    }

    public ResponseNoteDTO parseToResponse(RequestNoteDTO requestNoteDTO){
        return new ResponseNoteDTO(Long.valueOf(this.notes.size()+1), new Date(Calendar.getInstance().getTime().getTime()), requestNoteDTO.getMessage());
    }
}
