package rs.ac.uns.ftn.informatika.jpa.dto.request;

public class RequestPanicStringDTO {

    String reason;

    public RequestPanicStringDTO() {
    }

    public RequestPanicStringDTO(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
