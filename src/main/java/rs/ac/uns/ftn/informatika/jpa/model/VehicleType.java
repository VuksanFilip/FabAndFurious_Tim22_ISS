package rs.ac.uns.ftn.informatika.jpa.model;

public enum Type{
    STANDARD, LUXURY, VAN
}


public class VehicleType {
    Type type;
    float pricePerKm;

    public VehicleType() {
    }

    public VehicleType(Type type, float pricePerKm) {
        this.type = type;
        this.pricePerKm = pricePerKm;
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
