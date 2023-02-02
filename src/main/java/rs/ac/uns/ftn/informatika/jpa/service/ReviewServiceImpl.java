package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReviewDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseRideReviewsDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Review;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.enums.ReviewType;
import rs.ac.uns.ftn.informatika.jpa.repository.ReviewRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IReviewService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IRideService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements IReviewService {

    private ReviewRepository reviewRepository;
    private IRideService rideService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, IRideService rideService) {
        this.reviewRepository = reviewRepository;
        this.rideService = rideService;
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

    public List<Review> getReviewsForDriver(String id) {

        List<Review> reviewsForDriver = new ArrayList<>();
        for (Review r : getAll()) {
            if (r.getReviewType() == ReviewType.DRIVER && r.getRide().getDriver().getId().toString().equals(id)) {
                reviewsForDriver.add(r);
            }
        }
        return reviewsForDriver;
    }

    public List<Review> getRideReviewsForDriver(String id){

        Ride ride = this.rideService.getRide(id).get();

        List<Review> driverReviews = new ArrayList<>();
        for (Review r : ride.getReviews()) {
            if (r.getReviewType() == ReviewType.DRIVER) {
                driverReviews.add(r);
            }
        }
        return driverReviews;
    }

    public List<Review> getReviewsForVehicle(String id) {

        List<Review> reviewsForVehicle = new ArrayList<>();
        for (Review r : getAll()) {
            System.out.println(r.getRide().getVehicle().getId().toString());
            if (r.getReviewType() == ReviewType.VEHICLE && r.getRide().getVehicle().getId().toString().equals(id)) {
                reviewsForVehicle.add(r);
            }
        }
        return reviewsForVehicle;
    }

    public List<Review> getRideReviewsForVehicle(String id){

        Ride ride = this.rideService.getRide(id).get();

        List<Review> vehicleReviews = new ArrayList<>();
        for (Review r : ride.getReviews()) {
            if (r.getReviewType() == ReviewType.VEHICLE) {
                vehicleReviews.add(r);
            }
        }
        return vehicleReviews;
    }

    public List<ResponseRideReviewsDTO> getRideReviews(String id){

        Ride ride = this.rideService.getRide(id).get();

        List<ResponseRideReviewsDTO> responseRideReviewsDTOS = new ArrayList<>();
        ResponseRideReviewsDTO responseRideReviewsDTO = new ResponseRideReviewsDTO();

        for(Passenger p : ride.getPassengers()){
            for(Review r : ride.getReviews()){
                if(p.getId() == r.getPassenger().getId()){
                    if(r.getReviewType() == ReviewType.VEHICLE){
                        responseRideReviewsDTO.setVehicleReview(new ResponseReviewDTO(r));
                    }
                    if(r.getReviewType() == ReviewType.DRIVER){
                        responseRideReviewsDTO.setDriverReview(new ResponseReviewDTO(r));
                    }
                }
            }
            responseRideReviewsDTOS.add(responseRideReviewsDTO);
            responseRideReviewsDTO = new ResponseRideReviewsDTO();
        }
        return responseRideReviewsDTOS;
    }
}
