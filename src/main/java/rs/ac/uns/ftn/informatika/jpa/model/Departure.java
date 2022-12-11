package rs.ac.uns.ftn.informatika.jpa.model;

public class Departure extends Location{

    public Departure() {
    }

    public Departure(Long id, String address, double longitude, double latitude) {
        super(id, address, longitude, latitude);
    }
}
