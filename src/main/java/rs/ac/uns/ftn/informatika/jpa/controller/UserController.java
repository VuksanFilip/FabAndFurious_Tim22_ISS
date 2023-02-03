package rs.ac.uns.ftn.informatika.jpa.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.model.Note;
import rs.ac.uns.ftn.informatika.jpa.model.Ride;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.model.enums.Role;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;
import rs.ac.uns.ftn.informatika.jpa.util.TokenUtils;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final IPassengerService passengerService;
    private final IDriverService driverService;
    private final INoteService noteService;
    private final IMailService mailService;
    private final IRideService rideService;
    private final PasswordEncoder passwordEncoder;
    private final IMessageService messageService;

//    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    public UserController(IUserService userService, IPassengerService passengerService, IDriverService driverService, INoteService noteService, IMailService mailService, TokenUtils tokenUtils, AuthenticationManager authenticationManager, IRideService rideService, PasswordEncoder passwordEncoder, IMessageService messageService){
        this.userService = userService;
        this.passengerService = passengerService;
        this.driverService = driverService;
        this.noteService = noteService;
        this.mailService = mailService;
        this.tokenUtils = tokenUtils;
        this.passwordEncoder = passwordEncoder;
        this.rideService = rideService;
        this.messageService = messageService;
    }

    //RADI
    @PutMapping (value = "/{id}/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> changePassword(@PathVariable("id") String id, @RequestBody RequestUserChangePasswordDTO requestUserChangePasswordDTO) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!userService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }
        User user = userService.getUser(id).get();

        if(passwordEncoder.matches(requestUserChangePasswordDTO.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(requestUserChangePasswordDTO.getNewPassword()));
            userService.add(user);
            return new ResponseEntity<>(new MessageDTO("Password successfully changed!"), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(new MessageDTO("Current password is not matching!"), HttpStatus.BAD_REQUEST);
    }

    //RADI
    @GetMapping(value = "/{id}/resetPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> resetPassword(@PathVariable("id") String id) throws MessagingException, UnsupportedEncodingException {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!userService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }

        User user = userService.getUser(id).get();
        String token = String.valueOf(new Random().nextInt(900000) + 100000);
        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiration(LocalDateTime.now().plusMinutes(10));

        mailService.sendMail("filipvuksan.iphone@gmail.com", token);
        userService.add(user);

        return new ResponseEntity<>("Email with reset code has been sent!", HttpStatus.NO_CONTENT);
    }

    //RADI
    @PutMapping (value = "/{id}/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePasswordWithResetCode(@PathVariable("id") String id, @RequestBody RequestUserResetPasswordDTO requestUserResetPasswordDTO) {


        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!userService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }
        User user = userService.getUser(id).get();
        if (user.getResetPasswordToken() == null || user.getResetPasswordTokenExpiration().isBefore(LocalDateTime.now()) || !user.getResetPasswordToken().equals(requestUserResetPasswordDTO.getCode())) {
            return new ResponseEntity<>("Code is expired or not correct!", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(requestUserResetPasswordDTO.getNewPassword()));
        user.setResetPasswordToken(null);
        user.setResetPasswordTokenExpiration(null);
        userService.add(user);

        return new ResponseEntity<>("Password successfully changed!", HttpStatus.NO_CONTENT);
    }

    //RADI
    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getUserRides(@PathVariable("id") String id, Pageable page) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!userService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }
        User user = this.userService.getUser(id).get();

        List<ResponseRideNoStatusDTO> responseRides = new ArrayList<>();

        if(user.getRole() == Role.PASSENGER){
            Page<Ride> rides = this.rideService.getRidesForPassenger(user.getId().toString(), page);
            for(Ride r: rides){
                responseRides.add(r.parseToResponseNoStatus());
            }
            return new ResponseEntity<>(new ResponsePageDTO(rides.getNumberOfElements(), Arrays.asList(responseRides.toArray())), HttpStatus.OK);

        }
        else if(user.getRole() == Role.DRIVER){
            Page<Ride> rides = this.rideService.getRidesForDriver(user.getId().toString(), page);
            for(Ride r: rides){
                responseRides.add(r.parseToResponseNoStatus());
            }
            return new ResponseEntity<>(new ResponsePageDTO(rides.getNumberOfElements(), Arrays.asList(responseRides.toArray())), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Cant get rides for ADMIN"), HttpStatus.NOT_FOUND);
    }

    //RADI
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUsers(Pageable page) {

        Page<User> users = userService.findAll(page);
        int size = userService.getAll().size();
        List<ResponseUserWithIdDTO> responseUserDTOS = new ArrayList<>();
        for(User u: users){
            responseUserDTOS.add(u.parseToResponseUserWithId());
        }
        return new ResponseEntity<>(new ResponsePageDTO(size, Arrays.asList(responseUserDTOS.toArray())), HttpStatus.OK);
    }

    //RADI
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@Valid @RequestBody RequestLoginDTO login) {
        try{
            User user = this.userService.findByEmail(login.getEmail());
            ResponseLoginDTO responseLogin = new ResponseLoginDTO();
            responseLogin.setAccessToken(this.tokenUtils.generateToken(user));
            responseLogin.setRefreshToken(this.tokenUtils.generateRefreshToken(user));
            Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>(responseLogin, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new MessageDTO("Wrong username or password!"), HttpStatus.BAD_REQUEST);
        }

    }

    //RADI
    @PutMapping(value = "/{id}/block")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> blockUser(@PathVariable("id") String id){

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!userService.existsById(id)){
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

    //RADI
    @PutMapping(value = "/{id}/unblock")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> ublockUser(@PathVariable("id") String id){

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!userService.existsById(id)){
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

    //RADI
    @GetMapping(value = "/{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getUserMessages(@PathVariable("id") String id, Pageable page){

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!userService.getUser(id).isPresent()) {
            return new ResponseEntity(new MessageDTO("Receiver does not exist!"), HttpStatus.NOT_FOUND);
        }

        Page<Message> messages = this.messageService.getUserMessages(id, page);

        List<ResponseMessageDTO> responseMessageDTOS = new ArrayList<>();
        for(Message message : messages){
            responseMessageDTOS.add(message.parseToResponse());
        }
        return new ResponseEntity<>(new ResponsePageDTO(messages.getTotalPages(), Arrays.asList(responseMessageDTOS.toArray())), HttpStatus.OK);
    }

    //RADI
    @PostMapping(value = "/{id}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> sendMessageToUser(@PathVariable("id") String id, @RequestBody RequestMessageDTO requestMessageDTO){

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!userService.getUser(id).isPresent()) {
            return new ResponseEntity(new MessageDTO("Receiver does not exist!"), HttpStatus.NOT_FOUND);
        }

        User receiver = userService.getUser(id).get();

//        Integer idOfSender = this.userRequestValidation.getUserId(headers);

        String senderId = "2"; // kasnije zaminiti sa idofSender
        if (!userService.getUser(id).isPresent()) {
            return new ResponseEntity(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }

        User sender = userService.getUser(senderId).get();

        if (rideService.getRide(requestMessageDTO.getRideId().toString()) == null) {
            return new ResponseEntity("Ride does not exist!", HttpStatus.NOT_FOUND);
        }

        Message message = requestMessageDTO.parseToMessage(sender, receiver);
        this.messageService.add(message);

        return new ResponseEntity<>(message.parseToResponse(), HttpStatus.OK);
    }

    //RADI
    @PostMapping(value = "/{id}/note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> createNote(@PathVariable("id") String id, @RequestBody RequestNoteDTO requestNoteDTO){

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!userService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }

        User user = userService.getUser(id).get();
        Note note = requestNoteDTO.parseToNote(user);

        noteService.add(note);

        return new ResponseEntity<>(note.parseToResponse(), HttpStatus.OK);
    }

    //RADI
    @GetMapping(value = "/{id}/note", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getUserNotes(@PathVariable("id") String id, Pageable page){

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!userService.existsById(id)){
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }

        Page<Note> notes = noteService.getNotesByUserId(id, page);

        int size = noteService.getAll().size();
        List<ResponseNoteDTO> responseNoteDTOS = new ArrayList<>();
        for(Note n: notes){
            responseNoteDTOS.add(n.parseToResponse());
        }
        return new ResponseEntity<>(new ResponsePageDTO(size, Arrays.asList(responseNoteDTOS.toArray())), HttpStatus.OK);
    }

    @MessageMapping("/message")
    @SendTo("/topic/greetings")
    public String transferMessages(RequestMessageWithIdDTO requestMessageWithIdDTO) {

        Message message = new Message();
        User sender = this.userService.getUser(requestMessageWithIdDTO.getSender().toString()).get();
        message.setSender(sender);
        User receiver = this.userService.getUser(requestMessageWithIdDTO.getSender().toString()).get();
        message.setReciever(receiver);
        message.setRideId(requestMessageWithIdDTO.getRideId());
        message.setSendingTime(requestMessageWithIdDTO.getSentDateTime());
        message.setType(requestMessageWithIdDTO.getType());
        message.setMessage(requestMessageWithIdDTO.getMessage());
        this.messageService.add(message);
        return "LOL";
    }
}
