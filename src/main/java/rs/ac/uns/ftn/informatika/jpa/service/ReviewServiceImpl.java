package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Review;
import rs.ac.uns.ftn.informatika.jpa.repository.ReviewRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.ReviewService;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAll() {
        return (List<Review>) this.reviewRepository.findAll();
    }

    @Override
    public Optional<Review> getReview(String id) {
        return  this.reviewRepository.findById(Long.parseLong(id));
    }

    public void add(Review review) {
        this.reviewRepository.save(review);
    }
}
