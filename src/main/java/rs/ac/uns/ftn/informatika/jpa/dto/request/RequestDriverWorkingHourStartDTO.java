package rs.ac.uns.ftn.informatika.jpa.dto.request;

import java.util.Date;

public class RequestDriverWorkingHourStartDTO {

//    {
//            "start": "2022-12-11T14:58:53.730Z"
//    }
    private Date start;

    public RequestDriverWorkingHourStartDTO() {
    }

    public RequestDriverWorkingHourStartDTO(Date start) {
        this.start = start;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

}
