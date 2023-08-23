package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Chat;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.repository.ChatRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IChatService;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ChatServiceImpl implements IChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository){
        this.chatRepository = chatRepository;
    }

    @Override
    public List<Chat> getAllChatsForUser(Long userId) {
        List<Chat> allChats = this.chatRepository.findAll();
        List<Chat> chatsForUser = new ArrayList<>();
        for(Chat c : allChats){
            if(c.getUser1().getId() == userId || c.getUser2().getId() == userId){
                chatsForUser.add(c);
            }
        }
        return chatsForUser;
    }

    @Override
    public Optional<Chat> findChatById(Long chatId) {
        return this.chatRepository.findById(chatId);
    }

}
