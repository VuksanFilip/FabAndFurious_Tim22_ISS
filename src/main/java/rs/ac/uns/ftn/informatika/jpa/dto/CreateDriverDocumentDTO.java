package rs.ac.uns.ftn.informatika.jpa.dto;

import rs.ac.uns.ftn.informatika.jpa.model.Document;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.model.Review;

import java.util.Date;

public class CreateDriverDocumentDTO {

//    {
//        "name": "Vozačka dozvola",
//            "documentImage": "U3dhZ2dlciByb2Nrcw="
//    }

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
