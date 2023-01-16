package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestUnregisteredUserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseUnregisteredUserDTO;

@RestController
@RequestMapping("/api/unregisteredUser/")
public class UnregisteredUserController {


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUnregisteredUserDTO> createUnregisteredUser(@RequestBody RequestUnregisteredUserDTO unregisteredUser) throws Exception {
        ResponseUnregisteredUserDTO unregisteredUserResponse = unregisteredUser.parseToResponse();
        return new ResponseEntity<>(unregisteredUserResponse, HttpStatus.OK);
    }
}
