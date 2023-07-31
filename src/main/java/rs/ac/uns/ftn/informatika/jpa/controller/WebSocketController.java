package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessagingDTO;

@Controller
@RequestMapping("/api/websocket")
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send/{recipientId}")
    public void sendMessage(@DestinationVariable String recipientId, MessagingDTO message) {
        // Logic to handle messages, e.g., storing them in the database
        // Then broadcast the message to the recipient's topic
        messagingTemplate.convertAndSend("/topic/" + recipientId, message);
    }
}
