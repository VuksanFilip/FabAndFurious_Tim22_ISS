package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dummy.PanicDummy;
import rs.ac.uns.ftn.informatika.jpa.dto.response.AllPanicsResponseDTO;

@RestController
@RequestMapping("/api/panic")
public class PanicController {

    private PanicDummy panicDummy = new PanicDummy();

    @GetMapping
    public ResponseEntity<AllPanicsResponseDTO> getPanic() {
        return new ResponseEntity<AllPanicsResponseDTO>(this.panicDummy.parseToResponse(), HttpStatus.OK);
    }
}
