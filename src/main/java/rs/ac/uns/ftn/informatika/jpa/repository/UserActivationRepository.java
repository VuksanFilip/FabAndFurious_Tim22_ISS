package rs.ac.uns.ftn.informatika.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.informatika.jpa.model.UserActivation;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserActivationRepository extends JpaRepository<UserActivation, Long> {

    List<UserActivation> findAll();
    Optional<UserActivation> findById(String Long);

    @Query("select u from UserActivation u  where u.user.id = :userId")
    UserActivation findUserActivationByUserId(Long userId);



}