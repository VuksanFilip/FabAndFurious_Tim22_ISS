package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Location;

public class ResponseLocationDTO {

//    {
//        "address": "Bulevar oslobodjenja 46",
//            "latitude": 45.267136,
//            "longitude": 19.833549
//    }

    private String address;
    private double latitude;
    private double longitude;

    public ResponseLocationDTO() {
    }

    public ResponseLocationDTO(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Location parseToLocation(){
        return new Location(this.address, this.latitude, this.longitude);
    }
}
