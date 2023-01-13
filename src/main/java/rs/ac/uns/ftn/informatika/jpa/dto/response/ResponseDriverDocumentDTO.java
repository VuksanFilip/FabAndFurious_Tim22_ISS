package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Document;

public class ResponseDriverDocumentDTO {

//    {
//        "id": 123,
//            "name": "Vozačka dozvola",
//            "documentImage": "U3dhZ2dlciByb2Nrcw=",
//            "driverId": 10
//    }

    private Long id;
    private String name;
    private String documentImage;
    private Long driverId;

    public ResponseDriverDocumentDTO(Long id, String name, String documentImage, Long driverId) {
        this.id = id;
        this.name = name;
        this.documentImage = documentImage;
        this.driverId = driverId;
    }

    public ResponseDriverDocumentDTO(Document document) {
        this.id = document.getId();
        this.name = document.getName();
        this.documentImage = document.getImage();
        this.driverId = document.getDriver().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }
}
