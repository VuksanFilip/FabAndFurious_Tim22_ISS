package rs.ac.uns.ftn.informatika.jpa.dto.response;

public class ResponsePanicRideLocationDTO {

    private String address;
    private double latitude;
    private double longitude;

    public ResponsePanicRideLocationDTO() {
    }

    public ResponsePanicRideLocationDTO(String address, double latitude, double longitude) {
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
}
