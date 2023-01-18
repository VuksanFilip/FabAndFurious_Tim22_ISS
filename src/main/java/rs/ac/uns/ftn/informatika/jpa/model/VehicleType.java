package rs.ac.uns.ftn.informatika.jpa.model;

import lombok.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class VehicleType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleName vehicleName;
    private float pricePerKm;

    public VehicleType(VehicleName vehicleName){
        this.vehicleName = vehicleName;
    }

    public VehicleType(VehicleName vehicleName, float pricePerKm) {
        this.vehicleName = vehicleName;
        this.pricePerKm = pricePerKm;
    }
}
