package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.List;

public class ResponsePageDTO {

    int totalCount;
    List<Object> results;

    public ResponsePageDTO() {
    }

    public ResponsePageDTO(int results, List<Object> objects) {
        this.totalCount = results;
        this.results = objects;
    }

    public void asd(List<Object> objects){
        for(Object o: objects){
            results.add(o);
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Object> getResults() {
        return results;
    }

    public void setResults(List<Object> results) {
        this.results = results;
    }
}
