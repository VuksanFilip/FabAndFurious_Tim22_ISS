package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestEditDriverDTO;

public class ResponseEditRequestDTO {
    private String driverId;
    private RequestEditDriverDTO request;

    public ResponseEditRequestDTO() {
    }

    public ResponseEditRequestDTO(String driverId, RequestEditDriverDTO request) {
        this.driverId = driverId;
        this.request = request;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public RequestEditDriverDTO getRequest() {
        return request;
    }

    public void setRequest(RequestEditDriverDTO request) {
        this.request = request;
    }
}
