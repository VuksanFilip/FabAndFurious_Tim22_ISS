package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.enums.RideStatus;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import java.util.ArrayList;
import java.util.Date;

public class ResponseRideDTO {

    private Long id;
    private Date startTime;
    private Date endTime;
    private int totalCost;
    private ResponseDriverIdEmailDTO driver;
    private ArrayList<ResponsePassengerIdEmailDTO> passengers;
    private int estimatedTimeInMinutes;
    private VehicleName vehicleVehicleName;
    private boolean babyTransport;
    private boolean petTransport;
    private ResponseRejectionReasonTimeOfDetectionDTO rejection;
    private ArrayList<ResponseLocationDTO> locations;
    private RideStatus status;

    public ResponseRideDTO() {
    }

    public ResponseRideDTO(Date startTime, Date endTime, int totalCost, ResponseDriverIdEmailDTO driver, ArrayList<ResponsePassengerIdEmailDTO> passengers, int estimatedTimeInMinutes, VehicleName vehicleVehicleName, boolean babyTransport, boolean petTransport, ArrayList<ResponseLocationDTO> locations, RideStatus status, ResponseRejectionReasonTimeOfDetectionDTO rejection) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.vehicleVehicleName = vehicleVehicleName;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.locations = locations;
        this.status = status;
        this.rejection = rejection;
    }

    public ResponseRideDTO(Long id, Date startTime, Date endTime, int totalCost, ResponseDriverIdEmailDTO driver, ArrayList<ResponsePassengerIdEmailDTO> passengers, int estimatedTimeInMinutes, VehicleName vehicleVehicleName, boolean babyTransport, boolean petTransport, ResponseRejectionReasonTimeOfDetectionDTO rejection, ArrayList<ResponseLocationDTO> locations, RideStatus status) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalCost = totalCost;
        this.driver = driver;
        this.passengers = passengers;
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.vehicleVehicleName = vehicleVehicleName;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.rejection = rejection;
        this.locations = locations;
        this.status = status;
    }

    public ResponseRideDTO(Ride ride, ArrayList<ResponsePassengerIdEmailDTO> passengers, ArrayList<ResponseLocationDTO> locations, ResponseDriverIdEmailDTO driver, ResponseRejectionReasonTimeOfDetectionDTO rejection) {
        this.id = ride.getId();
        this.passengers = passengers;
        this.vehicleVehicleName = ride.getVehicle().getVehicleType().getVehicleName();
        this.babyTransport = ride.isBabyTransport();
        this.petTransport = ride.isPetTransport();
        this.locations = locations;
        this.startTime = ride.getStartTime();
        this.endTime = ride.getEndTime();
        this.totalCost = ride.getTotalCost();
        this.driver = driver;
        this.rejection = rejection;
        this.estimatedTimeInMinutes = ride.getEstimatedTimeInMinutes();
        this.status = ride.getStatus();
    }

    public ResponseRideDTO(Long id, ArrayList<ResponsePassengerIdEmailDTO> passengers, VehicleName vehicleVehicleName, boolean babyTransport, boolean petTransport, ArrayList<ResponseLocationDTO> locations) {
        this.id = id;
        this.passengers = passengers;
        this.vehicleVehicleName = vehicleVehicleName;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.locations = locations;
        this.startTime = null;
        this.endTime = null;
        this.totalCost = 0;
        this.driver = new ResponseDriverIdEmailDTO(1236L, null);
        this.rejection = new ResponseRejectionReasonTimeOfDetectionDTO();
        this.estimatedTimeInMinutes = 0;
        this.status = RideStatus.PENDING;
    }

    public ResponseRideDTO(Long id, ArrayList<ResponsePassengerIdEmailDTO> passengers, VehicleName vehicleVehicleName, boolean babyTransport, boolean petTransport, ArrayList<ResponseLocationDTO> locations, RideStatus status) {
        this.id = id;
        this.passengers = passengers;
        this.vehicleVehicleName = vehicleVehicleName;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.locations = locations;
        this.status = status;
    }

    public ResponseRideDTO(Long id, ArrayList<ResponsePassengerIdEmailDTO> passengers, VehicleName vehicleVehicleName, boolean babyTransport, boolean petTransport, ArrayList<ResponseLocationDTO> locations, RideStatus status, ResponseRejectionReasonTimeOfDetectionDTO rejection) {
        this.id = id;
        this.passengers = passengers;
        this.vehicleVehicleName = vehicleVehicleName;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
        this.locations = locations;
        this.startTime = null;
        this.endTime = null;
        this.totalCost = 0;
        this.driver = new ResponseDriverIdEmailDTO(123L, null);
        this.rejection = rejection;
        this.estimatedTimeInMinutes = 0;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public ResponseDriverIdEmailDTO getDriver() {
        return driver;
    }

    public void setDriver(ResponseDriverIdEmailDTO driver) {
        this.driver = driver;
    }

    public ArrayList<ResponsePassengerIdEmailDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<ResponsePassengerIdEmailDTO> passengers) {
        this.passengers = passengers;
    }

    public int getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(int estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public VehicleName getVehicleType() {
        return vehicleVehicleName;
    }

    public void setVehicleType(VehicleName vehicleVehicleName) {
        this.vehicleVehicleName = vehicleVehicleName;
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

    public ArrayList<ResponseLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<ResponseLocationDTO> locations) {
        this.locations = locations;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public ResponseRejectionReasonTimeOfDetectionDTO getRejection() {
        return rejection;
    }

    public void setRejection(ResponseRejectionReasonTimeOfDetectionDTO rejection) {
        this.rejection = rejection;
    }

    public void setDefaultResponse(){
        this.setId(123L);
        this.getStartTime();
        this.getEndTime();
        this.setTotalCost(12345);
        this.getDriver().setId(123L);
        this.getDriver().setEmail("user@example.com");
        this.getPassengers().get(0).setId(123L);
        this.getPassengers().get(0).setEmail("user@example.com");
        this.setEstimatedTimeInMinutes(5);
        this.setVehicleType(VehicleName.STANDARD);
        this.setBabyTransport(true);
        this.setPetTransport(true);
        this.getRejection().setReason("Ride is canceled due to previous problems with the passenger");
        this.getRejection().getTimeOfRejection();
        this.getLocations().get(0).getDeparture().setAddress("Bulevar oslobodjenja 46");
        this.getLocations().get(0).getDeparture().setLatitude(45.267136);
        this.getLocations().get(0).getDeparture().setLongitude(19.833549);
        this.getLocations().get(0).getDestination().setAddress("Bulevar oslobodjenja 46");
        this.getLocations().get(0).getDestination().setLatitude(45.267136);
        this.getLocations().get(0).getDestination().setLongitude(19.833549);
        this.setStatus(RideStatus.PENDING);
    }
}
