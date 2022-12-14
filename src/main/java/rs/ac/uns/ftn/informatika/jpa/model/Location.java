package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.LocationResponseDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Entity
@Inheritance
public class Location {

    @Id
    private Long id;
    private String address;
    private double longitude;
    private double latitude;

    public Location() {
    }

    public Location(Long id, String address, double longitude, double latitude) {
        this.id = id;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public Location(String address, double longitude, double latitude) {
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public LocationResponseDTO parseToResponse(){
        return new LocationResponseDTO(this.address, this.latitude, this.longitude);
    }
}
