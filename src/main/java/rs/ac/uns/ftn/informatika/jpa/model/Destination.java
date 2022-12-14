package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.Entity;

@Entity
public class Destination extends Location{

    public Destination() {
    }

    public Destination(Long id, String address, double longitude, double latitude) {
        super(id, address, longitude, latitude);
    }
}
