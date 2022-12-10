package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class DriverDocumentResponseDTO {

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

    public DriverDocumentResponseDTO(Long id, String name, String documentImage, Long driverId) {
        this.id = id;
        this.name = name;
        this.documentImage = documentImage;
        this.driverId = driverId;
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
