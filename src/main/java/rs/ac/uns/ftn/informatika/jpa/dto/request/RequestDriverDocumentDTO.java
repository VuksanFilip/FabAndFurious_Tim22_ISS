package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverDocumentDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Document;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDriverDocumentDTO {

    @NotBlank
    @Length(max = 50, message = "You have exceeded the allowed length (50)")
    private String name;

    @NotBlank(message = "Cant be empty")
    private String documentImage;

    public ResponseDriverDocumentDTO parseToResponse(Long id, Long driverId){
        ResponseDriverDocumentDTO driverDocumentResponse = new ResponseDriverDocumentDTO(id, this.name, this.documentImage, driverId);
        return driverDocumentResponse;
    }

    public Document parseToDocument(Driver driver){
        return new Document(this.name, this.documentImage, driver);
    }
}
