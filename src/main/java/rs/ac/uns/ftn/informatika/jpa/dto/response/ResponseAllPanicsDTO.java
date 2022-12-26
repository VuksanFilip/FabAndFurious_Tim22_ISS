package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.ArrayList;

public class ResponseAllPanicsDTO {

    private int totalCount;
    private ArrayList<ResponsePanicDTO> results;

    public ResponseAllPanicsDTO(int totalCount, ArrayList<ResponsePanicDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<ResponsePanicDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResponsePanicDTO> results) {
        this.results = results;
    }
}
