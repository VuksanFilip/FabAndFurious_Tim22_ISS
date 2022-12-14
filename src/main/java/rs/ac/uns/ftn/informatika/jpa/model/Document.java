package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverDocumentDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Document {

    @Id
    Long id;
    String name;
    String image;

    @OneToOne
    Driver driver;

    public Document() {
    }

    public Document(Long id, String name, String image, Long driverId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.driver = new Driver(driverId);
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public ResponseDriverDocumentDTO parseToResponse(){
        return new ResponseDriverDocumentDTO(this.getId(), this.getName(), this.getImage(), this.getDriver().getId());
    }
}
