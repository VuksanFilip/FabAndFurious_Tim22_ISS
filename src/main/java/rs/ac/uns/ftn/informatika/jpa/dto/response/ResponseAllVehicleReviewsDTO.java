package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseAllVehicleReviewsDTO {

    private int totalCount;
    private List<ResponseReviewDTO> results;

    public ResponseAllVehicleReviewsDTO(int totalCount, List<ResponseReviewDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ResponseReviewDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResponseReviewDTO> results) {
        this.results = results;
    }
}
