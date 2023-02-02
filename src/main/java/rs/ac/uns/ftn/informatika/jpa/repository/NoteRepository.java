package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.Note;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findAll();
    Optional<Note> findById(String Long);
    Page<Note> findAll(Pageable pageable);

    @Query("select n from Note n where n.user.id = :userId")
    Page<Note> getNotesByUserId(Long userId, Pageable page);


}