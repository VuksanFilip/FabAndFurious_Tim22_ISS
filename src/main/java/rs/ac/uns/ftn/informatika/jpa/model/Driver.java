package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseDriverDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicRideDriverDTO;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Driver extends User{

    Long id;

    @OneToMany
    List<Document> documents;

    @OneToMany
    List<Ride> rides;

    @OneToOne
    Vehicle vehicle;

    public Driver() {
    }

    public Driver(Long id) {
        super(id);
    }

    public Driver(Long id, String email) {
        super(id, email);
    }

    public Driver(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
    }

    public Driver(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password, boolean blocked, boolean active, ArrayList<Document> documents, ArrayList<Ride> rides, Vehicle vehicle) {
        super(id, firstName, lastName, picture, phoneNumber, email, address, password, blocked, active);
        this.documents = documents;
        this.rides = rides;
        this.vehicle = vehicle;
    }

    public Driver(Long id, String firstName, String lastName, String picture, String phoneNumber, String email, String address, String password){
        super(id, firstName, lastName, picture, phoneNumber, email, address, password);
        this.setBlocked(false);
        this.setActive(false);
        this.documents = new ArrayList<Document>();
        this.rides = new ArrayList<Ride>();
        this.vehicle = null;

    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ResponseDriverDTO parseToResponse(){
        return new ResponseDriverDTO(this.getId(), this.getFirstName(), this.getLastName(), this.getPicture(), this.getPhoneNumber(), this.getEmail(), this.getAddress());
    }

    public ResponsePanicRideDriverDTO parseToPanicDriverResponse(){
        return new ResponsePanicRideDriverDTO(this.getId(), this.getEmail());
    }
}
