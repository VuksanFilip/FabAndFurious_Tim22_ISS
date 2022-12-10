package rs.ac.uns.ftn.informatika.jpa.dto;
import rs.ac.uns.ftn.informatika.jpa.model.Panic;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;


import java.util.Date;

public class CreatePanicDTO {

    public CreatePanicDTO(){

    }

    public PanicResponseDTO parseToResponse(Long id){
        PanicResponseDTO panicResponse = new PanicResponseDTO();
        panicResponse.setId(id);
        return panicResponse;
    }

    public Panic parseToPanic(Long id, Date time, String reason, User user, Ride ride){

        return new Panic(id, time, reason, user, ride);
    }
}
