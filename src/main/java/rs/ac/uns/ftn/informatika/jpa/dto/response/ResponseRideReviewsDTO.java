package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class ResponseRideReviewsDTO {
    private ResponseReviewDTO vehicleReview;
    private ResponseReviewDTO driverReview;

    public ResponseRideReviewsDTO() {
    }

    public ResponseRideReviewsDTO(ResponseReviewDTO vehicleReview, ResponseReviewDTO driverReview) {
        this.vehicleReview = vehicleReview;
        this.driverReview = driverReview;
    }

    public ResponseReviewDTO getVehicleReview() {
        return vehicleReview;
    }

    public void setVehicleReview(ResponseReviewDTO vehicleReview) {
        this.vehicleReview = vehicleReview;
    }

    public ResponseReviewDTO getDriverReview() {
        return driverReview;
    }

    public void setDriverReview(ResponseReviewDTO driverReview) {
        this.driverReview = driverReview;
    }
}
