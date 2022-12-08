package rs.ac.uns.ftn.informatika.jpa.dto;
import rs.ac.uns.ftn.informatika.jpa.model.Panic;


import java.util.Date;

public class CreatePanicDTO {

    public CreatePanicDTO(){

    }

    public PanicResponseDTO parseToResponse(Long id){
        PanicResponseDTO panicResponse = new PanicResponseDTO();
        panicResponse.setId(id);
        return panicResponse;
    }

    public Panic parseToPanic(Long id, int totalCount, Date time, String reason){

        return new Panic(id, null, time, reason);
    }
}
