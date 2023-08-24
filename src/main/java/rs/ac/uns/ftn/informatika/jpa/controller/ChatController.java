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
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IChatService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final IChatService chatService;
    private final UserRepository userRepository;

    public ChatController(IChatService chatService, UserRepository userRepository){
        this.chatService = chatService;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllChatsForUser(@PathVariable("userId") Long userId){
        List<Chat> chats = this.chatService.getAllChatsForUser(userId);
        if(chats.size() == 0){
            return new ResponseEntity<>(new MessageDTO("There's none chats for this user"), HttpStatus.NOT_FOUND);
        }
        List<ResponseChatDTO> chatsDTO = new ArrayList<>();
        for (Chat c : chats){
            Long otherId = this.chatService.getOtherIdInChat(c, userId);
            String otherName = this.userRepository.findById(otherId).get().getFirstName() + " " + this.userRepository.findById(otherId).get().getLastName();
            chatsDTO.add(c.parseToDTO(userId, otherId, otherName));
        }
        return new ResponseEntity<>(chatsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getChatById(@PathVariable("chatId") Long chatId){
        if(!this.chatService.findChatById(chatId).isPresent()){
            return new ResponseEntity<>(new MessageDTO("Chat does not exist!"), HttpStatus.NOT_FOUND);
        }
        Chat chat = this.chatService.findChatById(chatId).get();
        return new ResponseEntity<>(chat.parseToDTO(Long.getLong("0"),Long.getLong("0"),""), HttpStatus.OK);
    }

    @GetMapping(value = "/{user1}/{user2}")
    public ResponseEntity<?> findChatId(@PathVariable("user1") Long user1Id, @PathVariable("user2") Long user2Id){
        return new ResponseEntity<>(this.chatService.chatForWebsocket(user1Id, user2Id), HttpStatus.OK);
    }
}
