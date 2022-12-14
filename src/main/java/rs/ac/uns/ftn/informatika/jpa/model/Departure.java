package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.Entity;

@Entity
public class Departure extends Location{

    public Departure() {
    }

    public Departure(Long id, String address, double longitude, double latitude) {
        super(id, address, longitude, latitude);
    }
}
