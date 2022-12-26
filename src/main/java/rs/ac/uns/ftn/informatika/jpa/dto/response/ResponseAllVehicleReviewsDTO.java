package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.ArrayList;

public class ResponseAllVehicleReviewsDTO {

    private int totalCount;
    private ArrayList<ResponseReviewDTO> results;

    public ResponseAllVehicleReviewsDTO(int totalCount, ArrayList<ResponseReviewDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<ResponseReviewDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResponseReviewDTO> results) {
        this.results = results;
    }
}
