package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestNoteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLoginDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseAllMessagesDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseLoginDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseMessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.ResponseNoteDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.NoteDummy;
import rs.ac.uns.ftn.informatika.jpa.dummy.UserDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserDummy userDummy = new UserDummy();

    @PutMapping(value = "/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable("id") Long id) throws Exception {
        User user = userDummy.users.get(id);
        user.setBlocked(true);

        return new ResponseEntity<String>("User is successfully blocked", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/unblock")
    public ResponseEntity<String> unblockUser(@PathVariable("id") Long id) throws Exception {
        User user = userDummy.users.get(id);
        user.setBlocked(false);

        return new ResponseEntity<String>("User is successfully unblocked", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseAllMessagesDTO> getUserMessages(@PathVariable("id") Long id) throws Exception{
        ArrayList<ResponseMessageDTO> responseMessageDTOS = new ArrayList<ResponseMessageDTO>();
        for(Message m : userDummy.getMessages(id)){
            responseMessageDTOS.add(m.parseToDTO());
        }
        return new ResponseEntity<ResponseAllMessagesDTO>(new ResponseAllMessagesDTO(responseMessageDTOS.size(), responseMessageDTOS), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseNoteDTO> createNote(@PathVariable("id") Long id, @RequestBody RequestNoteDTO requestNoteDTO){
        NoteDummy noteDummy = new NoteDummy();
        return new ResponseEntity<ResponseNoteDTO>(noteDummy.parseToResponse(requestNoteDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseLoginDTO> login(@RequestBody RequestLoginDTO requestLoginDTO){
        return new ResponseEntity<ResponseLoginDTO>(requestLoginDTO.parseToResponse(), HttpStatus.OK);
    }

}
