package rs.ac.uns.ftn.informatika.jpa.model.enums;

public enum VehicleName {
    STANDARD, LUXURY, VAN;

    public VehicleName getVehicleName(String name){
        if(name.equalsIgnoreCase("standard")) {
            return STANDARD;
        } else if (name.equalsIgnoreCase("luxury")) {
            return LUXURY;
        } else {
            return VAN;
        }
    }
}
