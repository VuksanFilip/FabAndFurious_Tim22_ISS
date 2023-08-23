package rs.ac.uns.ftn.informatika.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.Chat;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Long> {
    List<Chat> findAll();

    @Override
    Optional<Chat> findById(Long aLong);
}
