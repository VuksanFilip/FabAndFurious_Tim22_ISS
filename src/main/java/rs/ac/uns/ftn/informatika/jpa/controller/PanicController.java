package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreatePanicDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.PanicResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.PanicDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Panic;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/panic")
public class PanicController {

    private PanicDummy panicDummy = new PanicDummy();

    public void addPanic(){
        Integer i = 1;
        Long id = Long.valueOf(i.longValue());
        Integer ii = 2;
        Long idd = Long.valueOf(ii.longValue());
        this.panicDummy.panics.put(id, new Panic(id,null,"reasonn",null, null));
        this.panicDummy.panics.put(idd, new Panic(idd,null,"reasonn2",null, null));
    }


    @GetMapping
    public ResponseEntity<PanicResponseDTO> getPanic() {
        addPanic();
        Integer i = 1;
        Long id = Long.valueOf(i.longValue());
        Panic panic = panicDummy.panics.get(id);
        int numberOfPanics = panicDummy.panics.size();

        if (panic == null) {
            return new ResponseEntity<PanicResponseDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PanicResponseDTO>(panic.parseToResponse(numberOfPanics), HttpStatus.OK);
    }

}
