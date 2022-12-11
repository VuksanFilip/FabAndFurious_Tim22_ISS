package rs.ac.uns.ftn.informatika.jpa.model;

public class Destination extends Location{

    public Destination() {
    }

    public Destination(Long id, String address, double longitude, double latitude) {
        super(id, address, longitude, latitude);
    }
}
