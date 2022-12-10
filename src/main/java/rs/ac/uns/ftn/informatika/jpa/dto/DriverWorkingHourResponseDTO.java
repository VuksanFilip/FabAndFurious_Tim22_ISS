package rs.ac.uns.ftn.informatika.jpa.dto;

public class DriverWorkingHourResponseDTO {

//    {
//        "id": 10,
//            "start": "2022-12-10T22:23:40.346Z",
//            "end": "2022-12-10T22:23:40.346Z"
//    }

    private Long driverId;
    private String start;
    private String end;

    public DriverWorkingHourResponseDTO(Long driverId, String start, String end) {
        this.driverId = driverId;
        this.start = start;
        this.end = end;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
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
