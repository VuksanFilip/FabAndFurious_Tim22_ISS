package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.ArrayList;

public class AllVehicleReviewsResponseDTO {

    private int totalCount;
    private ArrayList<ReviewResponseDTO> results;

    public AllVehicleReviewsResponseDTO(int totalCount, ArrayList<ReviewResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<ReviewResponseDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<ReviewResponseDTO> results) {
        this.results = results;
    }
}
