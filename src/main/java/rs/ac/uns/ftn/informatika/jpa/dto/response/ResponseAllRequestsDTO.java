package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.List;

public class ResponseAllRequestsDTO {
    private List<ResponseEditRequestDTO> allRequests;

    public ResponseAllRequestsDTO() {
    }

    public ResponseAllRequestsDTO(List<ResponseEditRequestDTO> allRequests) {
        this.allRequests = allRequests;
    }

    public List<ResponseEditRequestDTO> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(List<ResponseEditRequestDTO> allRequests) {
        this.allRequests = allRequests;
    }
}
