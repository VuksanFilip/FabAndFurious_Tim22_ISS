package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.HopInMessage;

import java.util.ArrayList;
import java.util.List;

public class HopInAllMessagesDTO {

    private int totalCount;
    private List<HopInMessageReturnedDTO> results;

    public HopInAllMessagesDTO() {
    }

    public HopInAllMessagesDTO(List<HopInMessage> allMessages) {
        super();
        this.totalCount = allMessages.size();

        this.results = new ArrayList<HopInMessageReturnedDTO>();
        for (HopInMessage message : allMessages) {
            this.results.add(new HopInMessageReturnedDTO(message));
        }

    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<HopInMessageReturnedDTO> getResults() {
        return results;
    }

    public void setResults(List<HopInMessageReturnedDTO> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "AllMessagesDTO [totalCount=" + totalCount + ", results=" + results + "]";
    }

}

