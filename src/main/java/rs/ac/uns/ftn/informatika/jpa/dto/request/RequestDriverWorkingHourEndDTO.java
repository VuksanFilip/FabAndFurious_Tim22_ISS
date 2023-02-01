package rs.ac.uns.ftn.informatika.jpa.dto.request;

import java.time.LocalDateTime;

public class RequestDriverWorkingHourEndDTO {
    private LocalDateTime end;

    public RequestDriverWorkingHourEndDTO() {
    }

    public RequestDriverWorkingHourEndDTO(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
