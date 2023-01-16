package rs.ac.uns.ftn.informatika.jpa.dto.request;

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


}
