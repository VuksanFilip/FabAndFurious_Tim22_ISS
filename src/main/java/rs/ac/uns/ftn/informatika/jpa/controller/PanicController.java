package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponsePanicDTO;
import rs.ac.uns.ftn.informatika.jpa.model.Panic;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IPanicService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/panic")
public class PanicController {

    private IPanicService panicService;

    public PanicController(IPanicService panicService){
        this.panicService = panicService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponsePageDTO> getAllPanics(Pageable page) {
        Page<Panic> panics = panicService.findAll(page);
        int results = panicService.getAll().size();

        List<ResponsePanicDTO> responsePanicDTOS = new ArrayList<>();
        for (Panic p : panics) {
            responsePanicDTOS.add(p.parseToResponse());
        }

        return new ResponseEntity<>(new ResponsePageDTO(results, Arrays.asList(responsePanicDTOS.toArray())), HttpStatus.OK);
    }
}
