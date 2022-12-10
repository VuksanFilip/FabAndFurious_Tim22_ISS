package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.WorkHour;

public class CreateDriverWorkingHourDTO {
//    {
//        "id": 10,
//            "start": "2022-12-10T21:54:39.568Z",
//            "end": "2022-12-10T21:54:39.568Z"
//    }

    private Long id;
    private Long driverId;
    private String start;
    private String end;

    public CreateDriverWorkingHourDTO(Long id, String start, String end) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public DriverWorkingHourResponseDTO parseToResponse(Long id){
        DriverWorkingHourResponseDTO driverWorkingHourResponse = new DriverWorkingHourResponseDTO(id, this.start, this.end);
        return driverWorkingHourResponse;
    }

    public WorkHour parseToWorkingHour(Long id, Long driverId){
        return new WorkHour(id, driverId, this.start, this.end);
    }
}
