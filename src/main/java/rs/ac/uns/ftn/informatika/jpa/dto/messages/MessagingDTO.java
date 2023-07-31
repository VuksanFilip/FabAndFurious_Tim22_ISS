package rs.ac.uns.ftn.informatika.jpa.dto.messages;

public class MessagingDTO {

    private String sender;
    private String content;

    public MessagingDTO() {
    }

    public MessagingDTO(String sender, String content) {

        this.sender = sender;
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
