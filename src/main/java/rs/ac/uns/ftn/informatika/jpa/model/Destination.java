package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDestinationDTO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Destination{

    @Id
    private Long id;
    private String address;
    private double latitude;
    private double longitude;

    public Destination() {
    }

    public Destination(Long id, String address, double latitude, double longitude) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ResponseDestinationDTO parseToResponse(){
        return new ResponseDestinationDTO(this.address, this.latitude, this.longitude);
    }
}
