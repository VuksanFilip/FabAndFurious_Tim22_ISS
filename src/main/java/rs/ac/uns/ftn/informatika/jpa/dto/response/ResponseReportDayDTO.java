package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.time.LocalDate;
import java.util.Date;

public class ResponseReportDayDTO {
    private LocalDate date;
    private int count;

    public ResponseReportDayDTO() {
    }

    public ResponseReportDayDTO(LocalDate date, int count) {
        this.date = date;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
