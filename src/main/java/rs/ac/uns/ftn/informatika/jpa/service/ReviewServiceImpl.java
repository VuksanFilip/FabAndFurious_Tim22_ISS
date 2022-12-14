package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Review;
import rs.ac.uns.ftn.informatika.jpa.repository.ReviewRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IReviewService;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements IReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAll() {
        return (List<Review>) this.reviewRepository.findAll();
    }

    public Page<Review> findAll(Pageable page) {
        return reviewRepository.findAll(page);
    }


    @Override
    public Optional<Review> getReview(String id) {
        return  this.reviewRepository.findById(Long.parseLong(id));
    }

    public void add(Review review) {
        this.reviewRepository.save(review);
    }
}
