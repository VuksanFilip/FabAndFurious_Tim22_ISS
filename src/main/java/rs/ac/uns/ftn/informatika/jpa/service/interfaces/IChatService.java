package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import java.util.List;
import java.util.Optional;

import rs.ac.uns.ftn.informatika.jpa.model.Chat;

public interface IChatService {

    List<Chat> getAllChatsForUser(Long userId);
    Optional<Chat> findChatById(Long chatId);
}
