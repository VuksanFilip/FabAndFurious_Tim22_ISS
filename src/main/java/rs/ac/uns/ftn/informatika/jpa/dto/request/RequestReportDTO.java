package rs.ac.uns.ftn.informatika.jpa.dto.request;

import java.time.LocalDate;
import java.util.Date;

public class RequestReportDTO {
    private LocalDate from;
    private LocalDate to;

    public RequestReportDTO() {
    }

    public RequestReportDTO(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
