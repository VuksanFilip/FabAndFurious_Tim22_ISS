package rs.ac.uns.ftn.informatika.jpa.dto.request;

import java.util.Date;

public class RequestDriverWorkingHourEndDTO {
    private Date end;

    public RequestDriverWorkingHourEndDTO() {
    }

    public RequestDriverWorkingHourEndDTO(Date end) {
        this.end = end;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
