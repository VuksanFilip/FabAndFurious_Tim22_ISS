package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();
    Optional<User> findById(String Long);
    boolean existsById(String Long);
    Page<User> findAll(Pageable pageable);

    @Query("select u from User u where u.email = ?1 and u.password = ?2")
    User findByLogin(String email, String password);
    User findByEmail(String emial);

}