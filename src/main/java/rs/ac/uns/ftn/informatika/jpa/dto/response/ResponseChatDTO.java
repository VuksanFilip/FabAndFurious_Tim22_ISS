package rs.ac.uns.ftn.informatika.jpa.dto.response;

import java.util.List;

public class ResponseChatDTO {

    private Long chatId;
    private Long myId;
    private Long otherId;
    private String otherName;
    private List<ChatMessagesDTO> messages;

    public ResponseChatDTO(Long chatId, Long myId, Long otherId, String otherName, List<ChatMessagesDTO> messages) {
        this.chatId = chatId;
        this.myId = myId;
        this.otherId = otherId;
        this.otherName = otherName;
        this.messages = messages;
    }

    public ResponseChatDTO(){

    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getMyId() {
        return myId;
    }

    public void setMyId(Long myId) {
        this.myId = myId;
    }

    public Long getOtherId() {
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public List<ChatMessagesDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessagesDTO> messages) {
        this.messages = messages;
    }


}
