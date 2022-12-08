package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rs.ac.uns.ftn.informatika.jpa.dto.PanicResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.CreatePanicDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.PanicDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Panic;

public class PanicController {

    private PanicDummy panicDummy = new PanicDummy();

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PanicResponseDTO> getPanic(@PathVariable("id") Long id) {
        Panic panic = panicDummy.panics.get(id);
        if (panic == null) {
            return new ResponseEntity<PanicResponseDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<PanicResponseDTO>(panic.parseToResponse(), HttpStatus.OK);
    }
}
