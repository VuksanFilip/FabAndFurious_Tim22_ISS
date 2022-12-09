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

    @PutMapping(value = "/{id}/block")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> blockUser(@PathVariable Long id) throws Exception {
        User user = userDummy.users.get(id);
        user.setBlocked(true);

        return new ResponseEntity<String();
    }

}
