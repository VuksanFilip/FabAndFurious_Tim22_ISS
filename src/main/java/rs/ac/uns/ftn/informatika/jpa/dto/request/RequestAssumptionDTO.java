package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import java.util.List;

public class RequestAssumptionDTO {
    private List<RequestLocationAssumptionDTO> locations;
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public RequestAssumptionDTO() {
    }

    public RequestAssumptionDTO(List<RequestLocationAssumptionDTO> locations, VehicleName vehicleType, boolean babyTransport, boolean petTransport) {
        this.locations = locations;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public List<RequestLocationAssumptionDTO> getLocationDTOS() {
        return locations;
    }

    public void setLocationDTOS(List<RequestLocationAssumptionDTO> locations) {
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

    public List<RequestLocationAssumptionDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RequestLocationAssumptionDTO> locations) {
        this.locations = locations;
    }

}
