package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestCurrentLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationWithAddressDTO;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Inheritance
public class Location {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String address;
    private double latitude;
    private double longitude;

    public Location(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void update(RequestCurrentLocationDTO location){
        this.address = location.getAddress();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    public RequestLocationWithAddressDTO parseToResponse() {
        return new RequestLocationWithAddressDTO(this.address, this.latitude, this.longitude);
    }
}
