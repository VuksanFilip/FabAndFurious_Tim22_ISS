package rs.ac.uns.ftn.informatika.jpa.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Chat;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.repository.ChatRepository;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IChatService;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ChatServiceImpl implements IChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, UserRepository userRepository){
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
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

    @Override
    public Long chatForWebsocket(Long user1Id, Long user2Id) {
        List<Chat> allChats = this.chatRepository.findAll();
        Chat chat = new Chat();
        for (Chat c : allChats){
            if((c.getUser1().getId() == user1Id && c.getUser2().getId() == user2Id) || (c.getUser1().getId() == user2Id && c.getUser2().getId() == user1Id)){
                return c.getId();
            }
        }
        chat.setUser1(this.userRepository.findById(user1Id).get());
        chat.setUser2(this.userRepository.findById(user2Id).get());
        this.chatRepository.save(chat);
        return chat.getId();
    }

    @Override
    public Long getOtherIdInChat(Chat c, Long myId) {
        if(c.getUser1().getId() != myId){
            return c.getUser1().getId();
        }
        return c.getUser2().getId();
    }

}
