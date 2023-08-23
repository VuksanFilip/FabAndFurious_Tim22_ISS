package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseChatDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Chat;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IChatService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final IChatService chatService;

    public ChatController(IChatService chatService){
        this.chatService = chatService;
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllChatsForUser(@PathVariable("userId") Long userId){
        List<Chat> chats = this.chatService.getAllChatsForUser(userId);
        if(chats.size() == 0){
            return new ResponseEntity<>(new MessageDTO("There's none chats for this user"), HttpStatus.NOT_FOUND);
        }
        List<ResponseChatDTO> chatsDTO = new ArrayList<>();
        for (Chat c : chats){
            chatsDTO.add(c.parseToDTO());
        }
        return new ResponseEntity<>(chatsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getChatById(@PathVariable("chatId") Long chatId){
        if(!this.chatService.findChatById(chatId).isPresent()){
            return new ResponseEntity<>(new MessageDTO("Chat does not exist!"), HttpStatus.NOT_FOUND);
        }
        Chat chat = this.chatService.findChatById(chatId).get();
        return new ResponseEntity<>(chat.parseToDTO(), HttpStatus.OK);
    }
}