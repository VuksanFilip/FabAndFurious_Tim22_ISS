package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.Date;

public class ResponseDriverWorkingHourDTO {

//    {
//        "id": 10,
//            "start": "2022-12-11T15:24:10.627Z",
//            "endTime": "2022-12-11T15:24:10.627Z"
//    }

    private Long id;
    private Date start;
    private Date end;

    public ResponseDriverWorkingHourDTO() {
    }

    public ResponseDriverWorkingHourDTO(Long id, Date start, Date end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
