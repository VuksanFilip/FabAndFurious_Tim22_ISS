package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import java.util.Date;
import java.util.List;
public class RequestRideDTO {

    private List<RequestLocationDTO> locations;
    private List<ResponsePassengerIdEmailDTO> passengers;
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;
    private Date scheduledTime;

    public RequestRideDTO() {
    }

    public RequestRideDTO(List<RequestLocationDTO> locations, List<ResponsePassengerIdEmailDTO> passengers, VehicleName vehicleType, boolean babyTransport, boolean petTransport, Date scheduledTime) {
        this.locations = locations;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.scheduledTime = scheduledTime;
    }

    public List<RequestLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<RequestLocationDTO> locations) {
        this.locations = locations;
    }

    public List<ResponsePassengerIdEmailDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<ResponsePassengerIdEmailDTO> passengers) {
        this.passengers = passengers;
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

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }



}
