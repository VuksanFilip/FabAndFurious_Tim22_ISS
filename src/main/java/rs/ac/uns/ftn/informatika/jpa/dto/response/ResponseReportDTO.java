package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.List;

public class ResponseReportDTO {
    private float sum;
    private float average;
    private List<ResponseReportDayDTO> days;

    public ResponseReportDTO() {
    }

    public ResponseReportDTO(float sum, float average, List<ResponseReportDayDTO> days) {
        this.sum = sum;
        this.average = average;
        this.days = days;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public List<ResponseReportDayDTO> getDays() {
        return days;
    }

    public void setDays(List<ResponseReportDayDTO> days) {
        this.days = days;
    }
}
