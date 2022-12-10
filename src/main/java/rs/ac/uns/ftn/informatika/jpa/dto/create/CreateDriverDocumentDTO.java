package rs.ac.uns.ftn.informatika.jpa.dto.create;

import rs.ac.uns.ftn.informatika.jpa.dto.response.DriverDocumentResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Document;

public class CreateDriverDocumentDTO {

    private String name;
    private String documentImage;

    public CreateDriverDocumentDTO() {
    }

    public CreateDriverDocumentDTO(String name, String documentImage) {
        this.name = name;
        this.documentImage = documentImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentImage() {
        return documentImage;
    }

    public void setDocumentImage(String documentImage) {
        this.documentImage = documentImage;
    }

    public DriverDocumentResponseDTO parseToResponse(Long id, Long driverId){
        DriverDocumentResponseDTO driverDocumentResponse = new DriverDocumentResponseDTO(id, this.name, this.documentImage, driverId);
        driverDocumentResponse.setId(id);
        return driverDocumentResponse;
    }

    public Document parseToDocument(Long id, Long driverId){
        return new Document(id, this.name, this.documentImage, driverId);
    }
}
