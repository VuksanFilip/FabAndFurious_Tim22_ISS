package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Review;

import java.util.List;
import java.util.Optional;

public interface IReviewService {

    List<Review> getAll();

    Optional<Review> getReview(String id);

    void add(Review review);

    Page<Review> findAll(Pageable page);

}
