package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.model.RejectionLetter;

public class RequestRejectionLetterDTO {
    String reason;

    public RequestRejectionLetterDTO() {
    }

    public RequestRejectionLetterDTO(String reason) {
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
