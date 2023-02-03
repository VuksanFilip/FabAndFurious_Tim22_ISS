package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.model.Message;

import java.util.List;
import java.util.Optional;

public interface IMessageService {

    List<Message> getAll();

    Optional<Message> getMessage(String id);

    void add(Message message);

    Page<Message> getUserMessages(String userId, Pageable page);
}
