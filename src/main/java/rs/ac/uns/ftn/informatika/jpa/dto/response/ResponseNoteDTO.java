package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.Date;

public class ResponseNoteDTO {

    private Long id;
    private Date date;
    private String message;

    public ResponseNoteDTO() {
    }

    public ResponseNoteDTO(Long id, Date date, String message) {
        this.id = id;
        this.date = date;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
