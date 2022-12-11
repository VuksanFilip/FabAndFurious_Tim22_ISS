package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class DriverWorkingHourResponseDTO {

//    {
//        "id": 10,
//            "start": "2022-12-11T15:24:10.627Z",
//            "end": "2022-12-11T15:24:10.627Z"
//    }

    private Long id;
    private String start;
    private String end;

    public DriverWorkingHourResponseDTO() {
    }

    public DriverWorkingHourResponseDTO(Long id, String start, String end) {
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
