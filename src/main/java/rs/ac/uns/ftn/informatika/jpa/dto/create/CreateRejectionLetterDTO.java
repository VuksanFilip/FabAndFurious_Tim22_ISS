package rs.ac.uns.ftn.informatika.jpa.dto.create;

import rs.ac.uns.ftn.informatika.jpa.model.RejectionLetter;

public class CreateRejectionLetterDTO {
    String reason;

    public CreateRejectionLetterDTO() {
    }

    public CreateRejectionLetterDTO(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RejectionLetter parseToRejectionLetter(){
        return new RejectionLetter(this.reason);
    }
}
