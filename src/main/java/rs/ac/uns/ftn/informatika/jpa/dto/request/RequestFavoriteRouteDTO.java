package rs.ac.uns.ftn.informatika.jpa.dto.request;

import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
import rs.ac.uns.ftn.informatika.jpa.model.FavoriteRoute;
import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.model.Passenger;
import rs.ac.uns.ftn.informatika.jpa.model.Route;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import java.util.ArrayList;
import java.util.List;

public class RequestFavoriteRouteDTO {
//    {
//        "favoriteName": "Home - to - Work",
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

    private String favoriteName;
    private List<RequestLocationDTO> locations;
    private List<ResponsePassengerIdEmailDTO> passengers;
    private VehicleName vehicleType;
    private boolean babyTransport;
    private boolean petTransport;

    public RequestFavoriteRouteDTO() {
    }

    public RequestFavoriteRouteDTO(String favoriteName, List<RequestLocationDTO> locations, List<ResponsePassengerIdEmailDTO> passengers, VehicleName vehicleType, boolean babyTransport, boolean petTransport) {
        this.favoriteName = favoriteName;
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

    public FavoriteRoute parseToFavoriteRoute(){
        List<Route> routes = new ArrayList<>();
        List<Passenger> passengers = new ArrayList<>();
        for(RequestLocationDTO r : this.locations){
            Location departure = new Location(r.getDeparture().getAddress());
        }
        return new FavoriteRoute(this.favoriteName, routes, passengers, this.vehicleType, this.babyTransport, this.petTransport);
    }
}
