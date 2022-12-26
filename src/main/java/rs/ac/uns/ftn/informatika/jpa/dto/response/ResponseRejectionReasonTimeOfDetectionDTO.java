package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.Date;

public class ResponseRejectionReasonTimeOfDetectionDTO {
    String reason;
    Date timeOfRejection;

    public ResponseRejectionReasonTimeOfDetectionDTO() {
    }

    public ResponseRejectionReasonTimeOfDetectionDTO(String reason) {
        this.reason = reason;
    }

    public ResponseRejectionReasonTimeOfDetectionDTO(String reason, Date timeOfRejection) {
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
