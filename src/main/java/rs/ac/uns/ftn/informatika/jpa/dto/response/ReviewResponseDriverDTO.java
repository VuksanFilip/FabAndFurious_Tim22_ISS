package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Passenger;

public class ReviewResponseDriverDTO {

//    {
//        "totalCount": 243,
//            "results": [
//        {
//            "id": 123,
//                "rating": 3,
//                "comment": "The driver was driving too fast",
//                "passenger": {
//            "id": 123,
//                    "email": "user@example.com"
//        }
//        }
//  ]
//    }

    private int totalCount;

    private Long id;

    private float rating;

    private String comment;

    private Passenger passenger;

    public ReviewResponseDriverDTO() {
    }

    public ReviewResponseDriverDTO(int totalCount, Long id , float rating, String comment) {
        this.id = id;
        this.totalCount = totalCount;
        this.rating = rating;
        this.comment = comment;
        this.passenger = null;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
