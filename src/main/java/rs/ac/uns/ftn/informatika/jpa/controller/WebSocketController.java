package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ChatMessagesDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Chat;
import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.enums.MessageType;
import rs.ac.uns.ftn.informatika.jpa.repository.ChatRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.MessageRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import rs.ac.uns.ftn.informatika.jpa.service.ChatServiceImpl;

@Controller
public class WebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final ChatServiceImpl chatService;

    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate, UserRepository userRepository, MessageRepository messageRepository, ChatRepository chatRepository, ChatServiceImpl chatService){
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.chatService = chatService;
    }

    @MessageMapping("/send/message")
    public String sendMessageToChat(ChatMessagesDTO messageDTO) {
        User sender = this.userRepository.findById(messageDTO.getSenderId()).get();
        User receiver = this.userRepository.findById(messageDTO.getReceiverId()).get();
        Message message = new Message(sender, receiver, MessageType.RIDE, messageDTO.getMessage(), messageDTO.getSendingTime(), messageDTO.getRideId());
        this.messageRepository.save(message);
        Chat chat = this.chatRepository.findById(this.chatService.chatForWebsocket(sender.getId(), receiver.getId())).get();
        chat.getMessages().add(message);
        this.chatRepository.save(chat);

        this.simpMessagingTemplate.convertAndSend("/chat/" + chat.getId(), messageDTO.getMessage());
        return messageDTO.getMessage();
    }

}
