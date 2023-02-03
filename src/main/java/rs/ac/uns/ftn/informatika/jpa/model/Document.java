package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverDocumentDTO;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;

    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Driver driver;

    public Document(Long id, String name, String image, Long driverId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.driver = new Driver(driverId);
    }

    public Document(String name, String image, Long driverId) {
        this.name = name;
        this.image = image;
        this.driver = new Driver(driverId);
    }

    public Document(String name, String image, Driver driver) {
        this.name = name;
        this.image = image;
        this.driver = driver;
    }

    public ResponseDriverDocumentDTO parseToResponse(){
        return new ResponseDriverDocumentDTO(this.getId(), this.getName(), this.getImage(), this.getDriver().getId());
    }
}
