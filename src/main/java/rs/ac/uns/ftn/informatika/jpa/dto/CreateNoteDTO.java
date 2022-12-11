package rs.ac.uns.ftn.informatika.jpa.dto.create;

import rs.ac.uns.ftn.informatika.jpa.dto.response.NoteResponseDTO;

public class CreateNoteDTO {

//    {
//        "message": "The passenger has requested and after that aborted the ride"
//    }

    private String message;

    public CreateNoteDTO() {
    }

    public CreateNoteDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
