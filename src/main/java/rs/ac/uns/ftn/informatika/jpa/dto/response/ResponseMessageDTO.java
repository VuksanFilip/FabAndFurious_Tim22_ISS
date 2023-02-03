package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.enums.MessageType;

import java.util.Date;

public class ResponseMessageDTO {

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
//                "idOfRide": 123
//        }
//  ]
//    }

    private Long id;
    private Date timeOfSending;
    private Long senderId;
    private Long receiverId;
    private String message;
    private MessageType type;
    private Long rideId;

    public ResponseMessageDTO() {
    }

    public ResponseMessageDTO(Long id, Date timeOfSending, Long senderId, Long receiverId, String message, MessageType type, Long rideId) {
        this.id = id;
        this.timeOfSending = timeOfSending;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.type = type;
        this.rideId = rideId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimeOfSending() {
        return timeOfSending;
    }

    public void setTimeOfSending(Date timeOfSending) {
        this.timeOfSending = timeOfSending;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}
