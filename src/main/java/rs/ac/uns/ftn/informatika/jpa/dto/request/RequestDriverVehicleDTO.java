package rs.ac.uns.ftn.informatika.jpa.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;
import rs.ac.uns.ftn.informatika.jpa.model.Location;
import rs.ac.uns.ftn.informatika.jpa.model.Vehicle;
import rs.ac.uns.ftn.informatika.jpa.model.VehicleType;
import rs.ac.uns.ftn.informatika.jpa.model.enums.VehicleName;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDriverVehicleDTO {

    @Pattern(regexp = "STANDARD|LUXURY|VAN", flags = Pattern.Flag.CASE_INSENSITIVE, message = "must be STANDARD/LUXURY/VAN")
    private String vehicleType;

    @NotBlank(message = "Cant be empty")
    @Length(max = 50, message = "You have exceeded the allowed length (50)")
    private String model;

    @NotBlank(message = "Cant be empty")
    @Length(max = 50, message = "You have exceeded the allowed length (50)")
    private String licenseNumber;

    @NotNull
    private Location currentLocation;

    @NotNull
    @Min(1) @Max(10)
    private int passengerSeats;

    @NotNull
    private boolean babyTransport;

    @NotNull
    private boolean petTransport;

    public Vehicle parseToVehicle(Driver driver){
        return new Vehicle(driver, new VehicleType(VehicleName.valueOf(this.vehicleType)), this.model, this.licenseNumber, this.currentLocation, this.passengerSeats, this.babyTransport, this.petTransport);
    }
}
