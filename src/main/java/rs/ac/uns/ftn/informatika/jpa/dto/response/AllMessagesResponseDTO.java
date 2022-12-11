package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.ArrayList;

public class AllMessagesResponseDTO {


//    {
//        "totalCount": 243,
//            "results": [
//        {
//            "id": 10,
//                "timeOfSending": "2022-11-25T17:32:28Z",
//                "senderId": 123,
//                "receiverId": 123,
//                "message": "The driver is going on a longer route on purpose",
//                "type": "RIDE",
//                "rideId": 123
//        }
//  ]
//    }

    private int totalCount;
    private ArrayList<MessageResponseDTO> results;

    public AllMessagesResponseDTO() {
    }

    public AllMessagesResponseDTO(int totalCount, ArrayList<MessageResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<MessageResponseDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<MessageResponseDTO> results) {
        this.results = results;
    }
}
