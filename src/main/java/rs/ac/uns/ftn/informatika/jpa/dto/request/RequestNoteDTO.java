package rs.ac.uns.ftn.informatika.jpa.dto.request;

public class RequestNoteDTO {

//    {
//        "message": "The passenger has requested and after that aborted the ride"
//    }

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


}
