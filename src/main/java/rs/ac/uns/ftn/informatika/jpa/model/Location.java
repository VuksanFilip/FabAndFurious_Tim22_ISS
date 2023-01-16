package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationWithAddressDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDepartureDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDestinationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLocationDTO;

import javax.persistence.*;

@Entity
@Inheritance
public class Location {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String address;
    private double latitude;
    private double longitude;

    public Location() {
    }

    public Location(Long id, String address, double latitude, double longitude) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(String address, double latitude, double longitude) {
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

    public RequestLocationWithAddressDTO parseToResponse() {
        return new RequestLocationWithAddressDTO(this.address, this.latitude, this.longitude);
    }

}
