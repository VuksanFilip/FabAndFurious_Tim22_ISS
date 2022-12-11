package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.ArrayList;

public class AllPanicsResponseDTO {

    private int totalCount;
    private ArrayList<PanicResponseDTO> results;

    public AllPanicsResponseDTO(int totalCount, ArrayList<PanicResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<PanicResponseDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<PanicResponseDTO> results) {
        this.results = results;
    }
}
