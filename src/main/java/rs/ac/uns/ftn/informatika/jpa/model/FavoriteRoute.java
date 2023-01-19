package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class FavoriteRoute {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String favoriteName;

    @OneToOne(cascade = {CascadeType.ALL})
    private Route route;

    @ManyToMany
    @Column(name = "passenger_id")
    @JoinTable(name = "Passenger_FavoriteLocation",
            joinColumns = { @JoinColumn(name = "favoritepath_id") },
            inverseJoinColumns = { @JoinColumn(name = "passenger_id") }
    )
    private List<Passenger> passengers;

    @Enumerated
    private VehicleName vehicleVehicleName;
    private boolean babyTransport;
    private boolean petTransport;

    public FavoriteRoute(String favoriteName, Route route, List<Passenger> passengers, VehicleName vehicleVehicleName, boolean babyTransport, boolean petTransport) {
        this.favoriteName = favoriteName;
        this.route = route;
        this.passengers = passengers;
        this.vehicleVehicleName = vehicleVehicleName;
        this.babyTransport = babyTransport;
        this.petTransport = petTransport;
    }

    public ResponseFavoriteLocationsDTO parseToResponse(){

        ArrayList<ResponsePassengerIdEmailDTO> responsPassengerIdEmailDTOS = new ArrayList<>();
        for(Passenger p : passengers){
            responsPassengerIdEmailDTOS.add(new ResponsePassengerIdEmailDTO(p.getId(), p.getEmail()));
        }
        ArrayList<ResponseLocationDTO> responseLocationDTOS = new ArrayList<ResponseLocationDTO>();
        responseLocationDTOS.add(new ResponseLocationDTO(new ResponseDepartureDTO(route.getDeparture().getAddress(), route.getDeparture().getLatitude(), route.getDeparture().getLongitude()), new ResponseDestinationDTO(route.getDestination().getAddress(), route.getDestination().getLatitude(), route.getDestination().getLongitude())));
        return new ResponseFavoriteLocationsDTO(this.id, this.favoriteName, responseLocationDTOS, this.vehicleVehicleName, this.babyTransport, this.petTransport, this.passengers);
    }

    public List<ResponseFavoriteLocationsDTO> parseToResponseList(List<FavoriteRoute> favoriteLocations){
        List<ResponseFavoriteLocationsDTO> responseFavoriteLocations = new ArrayList<>();
        for(FavoriteRoute locations: favoriteLocations){
            responseFavoriteLocations.add(locations.parseToResponse());
        }
        return responseFavoriteLocations;
    }
}
