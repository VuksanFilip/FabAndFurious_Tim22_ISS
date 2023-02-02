package rs.ac.uns.ftn.informatika.jpa.dto.request;

import java.time.LocalDate;
import java.util.Date;

public class RequestReportDTO {
    private String from;
    private String to;

    public RequestReportDTO() {
    }

    public RequestReportDTO(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
