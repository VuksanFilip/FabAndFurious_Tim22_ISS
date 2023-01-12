package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.model.CurrentLocation;

public class RequestCurrentLocationDTO {

    private String address;
    private double latitude;
    private double longitude;

    public RequestCurrentLocationDTO() {
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

    public CurrentLocation parseToCurrentLocation(){
        return new CurrentLocation(this.address, this.latitude, this.longitude);
    }

}
