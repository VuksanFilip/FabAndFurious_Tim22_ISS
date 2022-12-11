package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreateUnregisteredUserDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.UnregisteredUserReponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.UnregisteredUserDummy;

@RestController
@RequestMapping("/api/unregisteredUser/")
public class UnregisteredUserController {
    private UnregisteredUserDummy unregisteredUserDummy = new UnregisteredUserDummy();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnregisteredUserReponseDTO> createUnregisteredUser(@RequestBody CreateUnregisteredUserDTO unregisteredUser) throws Exception {
        UnregisteredUserReponseDTO unregisteredUserResponse = unregisteredUser.parseToResponse();
        return new ResponseEntity<UnregisteredUserReponseDTO>(unregisteredUserResponse, HttpStatus.OK);
    }
}
