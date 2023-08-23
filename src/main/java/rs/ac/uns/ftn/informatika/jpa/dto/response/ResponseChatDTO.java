package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.List;

public class ResponseChatDTO {

    private Long id;
    private List<ChatMessagesDTO> messages;

    public ResponseChatDTO(Long id, List<ChatMessagesDTO> messages){
        this.id = id;
        this.messages = messages;
    }

    public ResponseChatDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ChatMessagesDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessagesDTO> messages) {
        this.messages = messages;
    }


}
