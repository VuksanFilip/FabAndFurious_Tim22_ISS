package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.model.Note;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.Date;
import java.util.Calendar;

public class RequestNoteDTO {

    private String message;

    public RequestNoteDTO() {
    }

    public RequestNoteDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Note parseToNote(User user){
        return new Note(this.message, new Date(), user);
    }
}
