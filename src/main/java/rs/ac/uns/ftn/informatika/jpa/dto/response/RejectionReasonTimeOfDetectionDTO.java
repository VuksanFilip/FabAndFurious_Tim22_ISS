package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.Date;

public class RejectionReasonTimeOfDetectionDTO {
    String reason;
    Date timeOfRejection;

    public RejectionReasonTimeOfDetectionDTO() {
    }

    public RejectionReasonTimeOfDetectionDTO(String reason) {
        this.reason = reason;
    }

    public RejectionReasonTimeOfDetectionDTO(String reason, Date timeOfRejection) {
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

    public void setTimeOfRejection(Date time) {
        this.timeOfRejection = timeOfRejection;
    }
}
