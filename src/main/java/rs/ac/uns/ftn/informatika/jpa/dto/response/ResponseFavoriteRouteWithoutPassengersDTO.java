package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import java.util.List;

public class ResponseFavoriteRouteWithoutPassengersDTO {

    private Long id;
    private String favoriteName;
    private List<RequestLocationDTO> locations;
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public ResponseFavoriteRouteWithoutPassengersDTO() {
    }

    public ResponseFavoriteRouteWithoutPassengersDTO(Long id, String favoriteName, List<RequestLocationDTO> locations, VehicleName vehicleType, boolean babyTransport, boolean petTransport) {
        this.id = id;
        this.favoriteName = favoriteName;
        this.locations = locations;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public List<RequestLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RequestLocationDTO> locations) {
        this.locations = locations;
    }

    public VehicleName getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleName vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isBabyTransport() {
        return babyTransport;
    }

    public void setBabyTransport(boolean babyTransport) {
        this.babyTransport = babyTransport;
    }

    public boolean isPetTransport() {
        return petTransport;
    }

    public void setPetTransport(boolean petTransport) {
        this.petTransport = petTransport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
