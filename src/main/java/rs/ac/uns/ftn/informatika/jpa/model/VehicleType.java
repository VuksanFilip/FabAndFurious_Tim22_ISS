package rs.ac.uns.ftn.informatika.jpa.model;

import javax.persistence.*;

@Entity
public class VehicleType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    Type type;
    float pricePerKm;

    public VehicleType() {
    }

    public VehicleType(Type type){
        this.type = type;
    }

    public VehicleType(Type type, float pricePerKm) {
        this.type = type;
        this.pricePerKm = pricePerKm;
    }

    public VehicleType(VehicleType type) {
        this.type = type.getType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public float getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(float pricePerKm) {
        this.pricePerKm = pricePerKm;
    }
}
