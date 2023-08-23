package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.repository.MessageRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IMessageService;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements IMessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAll() {
        return (List<Message>) this.messageRepository.findAll();
    }

    @Override
    public Optional<Message> getMessage(String id) {
        return  this.messageRepository.findById(Long.parseLong(id));
    }

    public void add(Message message) {
        this.messageRepository.save(message);
    }

    public Page<Message> getUserMessages(String userId, Pageable page){
        return this.messageRepository.getUserMessages(Long.parseLong(userId), page);
    }
}
