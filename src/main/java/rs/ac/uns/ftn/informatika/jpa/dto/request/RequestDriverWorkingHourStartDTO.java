package rs.ac.uns.ftn.informatika.jpa.dto.request;

import java.time.LocalDateTime;

public class RequestDriverWorkingHourStartDTO {

    private LocalDateTime start;

    public RequestDriverWorkingHourStartDTO() {
    }

    public RequestDriverWorkingHourStartDTO(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

}
