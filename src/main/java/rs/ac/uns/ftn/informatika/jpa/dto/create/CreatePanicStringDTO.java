package rs.ac.uns.ftn.informatika.jpa.dto.create;

public class CreatePanicStringDTO {

    String reason;

    public CreatePanicStringDTO() {
    }

    public CreatePanicStringDTO(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
