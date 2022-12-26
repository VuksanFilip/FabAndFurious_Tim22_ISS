package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.Date;

public class ResponseNoteDTO {

//    {
//        "id": 10,
//            "date": "2022-11-25T17:32:28Z",
//            "message": "The passenger has requested and after that aborted the ride"
//    }

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
