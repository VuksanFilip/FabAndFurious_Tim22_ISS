package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseFavoriteLocationsDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLocationDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePassengerIdEmailDTO;
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
//        for(Location l : route){
//            responseLocationDTOS.add(new ResponseLocationDTO(l.getDeparture().parseToResponse(), l.getDestination().parseToResponse()));
//        }
        return new ResponseFavoriteLocationsDTO(this.id, this.favoriteName, responseLocationDTOS, this.vehicleVehicleName, this.babyTransport, this.petTransport);
    }

    public List<ResponseFavoriteLocationsDTO> parseToResponseList(List<FavoriteRoute> favoriteLocations){
        List<ResponseFavoriteLocationsDTO> responseFavoriteLocations = new ArrayList<>();
        for(FavoriteRoute locations: favoriteLocations){
            responseFavoriteLocations.add(locations.parseToResponse());
        }
        return responseFavoriteLocations;
    }
}
