package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestLoginDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestNoteDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestUserChangePasswordDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.RequestUserResetPasswordDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.model.Note;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private AuthenticationManager authenticationManager;
    private IUserService userService;
    private IPassengerService passengerService;
    private IDriverService driverService;
    private INoteService noteService;
    private IMailService mailService;

    public UserController(IUserService userService, IPassengerService passengerService, IDriverService driverService, INoteService noteService, IMailService mailService){
        this.userService = userService;
        this.passengerService = passengerService;
        this.driverService = driverService;
        this.noteService = noteService;
        this.mailService = mailService;
    }

    @PutMapping (value = "/{id}/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@PathVariable("id") String id, @RequestBody RequestUserChangePasswordDTO requestUserChangePasswordDTO) {

        if(!userService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }
        User user = userService.getUser(id).get();
        if(user.getPassword().equals(requestUserChangePasswordDTO.getOldPassword())){
            user.setPassword(requestUserChangePasswordDTO.getNewPassword());
            userService.add(user);
            return new ResponseEntity<>(new MessageDTO("Password successfully changed!"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new MessageDTO("Current password is not matching!"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}/resetPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@PathVariable("id") String id) throws MessagingException, UnsupportedEncodingException {

        if(!userService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }

        User user = userService.getUser(id).get();
        String token = String.valueOf(new Random().nextInt(900000) + 100000);
        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiration(LocalDateTime.now().plusMinutes(10));

        mailService.sendMail("filipvuksan.iphone@gmail.com", token);
        userService.add(user);
        //TODO TU POSLATI MAIL(Ne radi nesto MailServiceImpl-po komentarom je)

        return new ResponseEntity<>("Email with reset code has been sent!", HttpStatus.NO_CONTENT);
    }

    @PutMapping (value = "/{id}/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePasswordWithResetCode(@PathVariable("id") String id, @RequestBody RequestUserResetPasswordDTO requestUserResetPasswordDTO) {

        if(!userService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }
        User user = userService.getUser(id).get();
        if (user.getResetPasswordToken() == null || user.getResetPasswordTokenExpiration().isBefore(LocalDateTime.now()) || !user.getResetPasswordToken().equals(requestUserResetPasswordDTO.getCode())) {
            return new ResponseEntity<>("Code is expired or not correct!", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(requestUserResetPasswordDTO.getNewPassword());
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiration(null);
        userService.add(user);

        return new ResponseEntity<>("Password successfully changed!", HttpStatus.NO_CONTENT);
    }

    //TODO NAPRAVITI DA BUDE PAGEBLE (ZAJEBANO)
    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserRides(@PathVariable("id") String id, Pageable page) {

        List<ResponseRideNoStatusDTO> responseRides = new ArrayList<>();
        if(passengerService.existsById(id)){
            List<Ride> rides = passengerService.getPassenger(id).get().getRides();
            for(Ride r: rides){
                responseRides.add(r.parseToResponseNoStatusForUser());
            }
            return new ResponseEntity<>(responseRides, HttpStatus.NOT_FOUND);
        }
        else if(driverService.existsById(id)){
            List<Ride> rides = driverService.getDriver(id).get().getRides();
            for(Ride r: rides){
                responseRides.add(r.parseToResponseNoStatusForUser());
            }
            return new ResponseEntity<>(responseRides, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(Pageable page) {

        Page<User> users = userService.findAll(page);
        int size = userService.getAll().size();
        List<ResponseUserWithIdDTO> responseUserDTOS = new ArrayList<>();
        for(User u: users){
            responseUserDTOS.add(u.parseToResponseUserWithId());
        }
        return new ResponseEntity<>(new ResponsePageDTO(size, Arrays.asList(responseUserDTOS.toArray())), HttpStatus.NOT_FOUND);
    }

    //TODO OVDE SE TOKENI RADE
//    @PostMapping(value = "/login", consumes = "application/json")
//    public ResponseEntity<?> login(@RequestBody RequestLoginDTO authenticationRequest, HttpServletResponse response) {
//
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                authenticationRequest.getEmail(), authenticationRequest.getPassword()));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        User user = (User) authentication.getPrincipal();
//        String jwt = tokenUtils.generateToken(user.getUsername());
//
//        return ResponseEntity.ok(new TokenDTO(jwt, jwt));
//    }

    @PutMapping(value = "/{id}/block")
    public ResponseEntity<?> blockUser(@PathVariable("id") String id){

        if(userService.existsById(id) == false){
            return new ResponseEntity<>(new MessageDTO("Message placeholder (User does not exist!)"), HttpStatus.NOT_FOUND);
        }
        User user = userService.getUser(id).get();
        if(user.isBlocked()){
            return new ResponseEntity<>("User already blocked!", HttpStatus.BAD_REQUEST);
        }

        user.setBlocked(true);
        userService.add(user);
        return new ResponseEntity<>("User is successfully blocked", HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}/unblock")
    public ResponseEntity<?> ublockUser(@PathVariable("id") String id){

        if(userService.existsById(id) == false){
            return new ResponseEntity<>(new MessageDTO("Message placeholder (User does not exist!)"), HttpStatus.NOT_FOUND);
        }
        User user = userService.getUser(id).get();
        if(!user.isBlocked()){
            return new ResponseEntity<>("User already unblocked!", HttpStatus.BAD_REQUEST);
        }

        user.setBlocked(false);
        userService.add(user);
        return new ResponseEntity<>("User is successfully ublocked", HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserMessages(@PathVariable("id") String id){
        Set<ResponseMessageDTO> messageDTOS = userService.findMessagesOfUser(id);
        return new ResponseEntity<>(new ResponseMessagePageDTO(messageDTOS.size(), messageDTOS), HttpStatus.OK);
    }

    //TODO IMA VEZE SA TOKENIMA(ZAJEBANO)
    @PostMapping(value = "/{id}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendMessageToUser(@PathVariable("id") String id){
        return null;
    }

    @PostMapping(value = "/{id}/note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNote(@PathVariable("id") String id, @RequestBody RequestNoteDTO requestNoteDTO){
        if(userService.existsById(id) == false){
            return new ResponseEntity<>(new MessageDTO("Message placeholder (User does not exist!)"), HttpStatus.NOT_FOUND);
        }
        User user = userService.getUser(id).get();
        Note note = requestNoteDTO.parseToNote(user);
        noteService.add(note);
        return new ResponseEntity<>(note.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/note", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserNotes(@PathVariable("id") String id, Pageable page){
        if(userService.existsById(id) == false){
            return new ResponseEntity<>(new MessageDTO("Message placeholder (User does not exist!)"), HttpStatus.NOT_FOUND);
        }
        User user = userService.getUser(id).get();
        Page<Note> notes = noteService.findAll(page);
        int size = noteService.getAll().size();
        List<ResponseNoteDTO> responseNoteDTOS = new ArrayList<>();
        for(Note n: notes){
            if(n.getUser().getId().equals(user.getId())){
                responseNoteDTOS.add(n.parseToResponse());
            }
        }
        return new ResponseEntity<>(new ResponsePageDTO(size, Arrays.asList(responseNoteDTOS.toArray())), HttpStatus.OK);
    }
}
