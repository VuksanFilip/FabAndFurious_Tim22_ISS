package rs.ac.uns.ftn.informatika.jpa.dto.response;

import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;
import java.util.Date;
import java.util.List;

public class ResponseFavoriteRouteWithScheduledTimeDTO {

//    {
//        "id": 123,
//            "favoriteName": "Home - to - Work",
//            "scheduledTime": "2023-01-11T17:45:00Z",
//            "locations": [
//        {
//            "departure": {
//            "address": "Bulevar oslobodjenja 46",
//                    "latitude": 45.267136,
//                    "longitude": 19.833549
//        },
//            "destination": {
//            "address": "Bulevar oslobodjenja 46",
//                    "latitude": 45.267136,
//                    "longitude": 19.833549
//        }
//        }
//  ],
//        "passengers": [
//        {
//            "id": 123,
//                "email": "user@example.com"
//        }
//  ],
//        "vehicleType": "STANDARD",
//            "babyTransport": true,
//            "petTransport": true
//    }

    private Long id;
    private String favoriteName;
    private Date scheduledTime;
    private List<RequestLocationDTO> locations;
    private List<ResponsePassengerIdEmailDTO> passengers;
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public ResponseFavoriteRouteWithScheduledTimeDTO() {
    }

    public ResponseFavoriteRouteWithScheduledTimeDTO(Long id, String favoriteName, Date scheduledTime, List<RequestLocationDTO> locations, List<ResponsePassengerIdEmailDTO> passengers, VehicleName vehicleType, boolean babyTransport, boolean petTransport) {
        this.id = id;
        this.favoriteName = favoriteName;
        this.scheduledTime = scheduledTime;
        this.locations = locations;
        this.passengers = passengers;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
