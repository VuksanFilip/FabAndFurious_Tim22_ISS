package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.ArrayList;

public class ResponseAllMessagesDTO {


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
    private ArrayList<ResponseMessageDTO> results;

    public ResponseAllMessagesDTO() {
    }

    public ResponseAllMessagesDTO(int totalCount, ArrayList<ResponseMessageDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<ResponseMessageDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResponseMessageDTO> results) {
        this.results = results;
    }
}
