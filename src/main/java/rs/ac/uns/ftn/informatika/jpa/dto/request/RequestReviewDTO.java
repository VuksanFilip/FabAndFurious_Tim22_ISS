package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseReviewDTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestReviewDTO {

    @Min(1) @Max(5)
    private int rating;

    @NotBlank(message = "Cant be empty")
    @Length(max = 500, message = "You have exceeded the allowed length (500)")
    private String comment;

    public ResponseReviewDTO parseToResponse(Long id){
        ResponseReviewDTO reviewResponse = new ResponseReviewDTO(this.rating, this.comment);
        reviewResponse.setId(id);
        return reviewResponse;
    }
}
