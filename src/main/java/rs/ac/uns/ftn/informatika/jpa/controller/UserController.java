package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dummy.UserDummy;
import rs.ac.uns.ftn.informatika.jpa.model.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserDummy userDummy = new UserDummy();
    public void addUser(){
        Integer i = 1;
        Long id = Long.valueOf(i.longValue());
        this.userDummy.users.put(id, new User(id, "Pera", "Peric", "picture", "0655555555", "pera.peric@email.com", "address", "pera123"));
    }

    @PutMapping(value = "/{id}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long id) throws Exception {
        addUser();
        User user = userDummy.users.get(id);
        user.setBlocked(true);

        return new ResponseEntity<String>("User is successfully blocked", HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/unblock")
    public ResponseEntity<String> unblockUser(@PathVariable Long id) throws Exception {
        addUser();
        User user = userDummy.users.get(id);
        user.setBlocked(false);

        return new ResponseEntity<String>("User is successfully unblocked", HttpStatus.OK);
    }

}
