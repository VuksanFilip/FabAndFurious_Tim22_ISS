package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< Updated upstream
import rs.ac.uns.ftn.informatika.jpa.dto.PanicResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CreatePanicDTO;
=======
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreatePanicDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.AllPanicsResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.PanicResponseDTO;
>>>>>>> Stashed changes
import rs.ac.uns.ftn.informatika.jpa.dummy.PanicDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Panic;


@RestController
@RequestMapping("/api/panics")
public class PanicController {

    private PanicDummy panicDummy = new PanicDummy();

<<<<<<< Updated upstream
    public void addPanic(){
        Integer i = 1;
        Long id = Long.valueOf(i.longValue());
        this.panicDummy.panics.put(id, new Panic(id,null,"reasonn",null, null));
    }


    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PanicResponseDTO> getPanic(@PathVariable("id") Long id) {
        addPanic();
        Panic panic = panicDummy.panics.get(id);
        int numberOfPanics = panicDummy.panics.size();

        if (panic == null) {
            return new ResponseEntity<PanicResponseDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PanicResponseDTO>(panic.parseToResponse(3), HttpStatus.OK);
=======
    @GetMapping
    public ResponseEntity<AllPanicsResponseDTO> getPanic() {
        return new ResponseEntity<AllPanicsResponseDTO>(this.panicDummy.parseToResponse(), HttpStatus.OK);
>>>>>>> Stashed changes
    }
}
