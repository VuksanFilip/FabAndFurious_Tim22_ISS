package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.Date;

public class RejectionReasonTimeOfDetectionDTO {
    String reason;
    Date time;

    public RejectionReasonTimeOfDetectionDTO() {
    }

    public RejectionReasonTimeOfDetectionDTO(String reason) {
        this.reason = reason;
    }

    public RejectionReasonTimeOfDetectionDTO(String reason, Date time) {
        this.reason = reason;
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
