package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.HopInMessage;

import java.util.List;

@Repository
public interface HopInMessageRepository extends JpaRepository<HopInMessage, Long> {

    @Query("select m from HopInMessage m where m.receiverId = ?1 or m.senderId = ?1")
    public List<HopInMessage> findAllMessagesById(Long id);
}
