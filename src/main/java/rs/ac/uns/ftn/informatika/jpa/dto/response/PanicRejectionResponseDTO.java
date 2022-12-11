package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.Date;

public class PanicRejectionResponseDTO {

    private String reason;
    private Date timeOfRejection;

    public PanicRejectionResponseDTO(String reason, Date timeOfRejection) {
        this.reason = reason;
        this.timeOfRejection = timeOfRejection;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getTimeOfRejection() {
        return timeOfRejection;
    }

    public void setTimeOfRejection(Date timeOfRejection) {
        this.timeOfRejection = timeOfRejection;
    }
}
