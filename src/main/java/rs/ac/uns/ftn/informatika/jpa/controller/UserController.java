package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.create.CreateNoteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.create.UserLoginDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.AllMessagesResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.LoginResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.MessageResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.NoteResponseDTO;
import rs.ac.uns.ftn.informatika.jpa.dummy.NoteDummy;
import rs.ac.uns.ftn.informatika.jpa.dummy.UserDummy;
import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.lang.reflect.Array;
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
    public ResponseEntity<AllMessagesResponseDTO> getUserMessages(@PathVariable("id") Long id) throws Exception{
        ArrayList<MessageResponseDTO> messageResponseDTOS = new ArrayList<MessageResponseDTO>();
        for(Message m : userDummy.getMessages(id)){
            messageResponseDTOS.add(m.parseToDTO());
        }
        return new ResponseEntity<AllMessagesResponseDTO>(new AllMessagesResponseDTO(messageResponseDTOS.size(), messageResponseDTOS), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteResponseDTO> createNote(@PathVariable("id") Long id, @RequestBody CreateNoteDTO createNoteDTO){
        NoteDummy noteDummy = new NoteDummy();
        return new ResponseEntity<NoteResponseDTO>(noteDummy.parseToResponse(createNoteDTO), HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO){
        return new ResponseEntity<LoginResponseDTO>(userLoginDTO.parseToResponse(), HttpStatus.OK);
    }

}
