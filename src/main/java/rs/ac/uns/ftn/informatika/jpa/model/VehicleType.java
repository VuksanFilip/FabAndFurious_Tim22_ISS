package rs.ac.uns.ftn.informatika.jpa.model;

import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import javax.persistence.*;

@Entity
public class VehicleType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleName vehicleName;
    private float pricePerKm;

    public VehicleType() {
    }

    public VehicleType(VehicleName vehicleName){
        this.vehicleName = vehicleName;
    }

    public VehicleType(VehicleName vehicleName, float pricePerKm) {
        this.vehicleName = vehicleName;
        this.pricePerKm = pricePerKm;
    }

    public VehicleType(VehicleType type) {
        this.vehicleName = type.getType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleName getType() {
        return vehicleName;
    }

    public void setType(VehicleName vehicleName) {
        this.vehicleName = vehicleName;
    }

    public float getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(float pricePerKm) {
        this.pricePerKm = pricePerKm;
    }
}
