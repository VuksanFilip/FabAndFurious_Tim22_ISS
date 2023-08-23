package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.repository.ChatRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IChatService;

@Service
public class ChatServiceImpl implements IChatService {

    private final ChatRepository chatRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository){
        this.chatRepository = chatRepository;
    }
}
