package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class ChatMessagesDTO {

    private String senderEmail;
    private String receiverEmail;
    private String message;
    private Long rideId;

    public ChatMessagesDTO() {
    }

    public ChatMessagesDTO(String senderEmail, String receiverEmail, String message, Long rideId) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.message = message;
        this.rideId = rideId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}
