package rs.ac.uns.ftn.informatika.jpa.dto.create;

import rs.ac.uns.ftn.informatika.jpa.dto.response.DriverDocumentResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.DriverVehicleResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.DriverWorkingHourResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.model.WorkHour;

public class CreateDriverWorkingHourDTO {

//    {
//        "id": 10,
//            "start": "2022-12-11T14:58:53.730Z",
//            "end": "2022-12-11T14:58:53.730Z"
//    }
    private Long id;
    private String start;
    private String end;

    public CreateDriverWorkingHourDTO() {
    }

    public CreateDriverWorkingHourDTO(Long id, String start, String end) {
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

    public DriverWorkingHourResponseDTO parseToResponse(Long id){
        DriverWorkingHourResponseDTO driverWorkingHourResponse = new DriverWorkingHourResponseDTO(id, this.start, this.end);
        return driverWorkingHourResponse;
    }

    public WorkHour parseToWorkHour(Long id){
        return new WorkHour(id, this.start, this.end);
    }
}
