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
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
import rs.ac.uns.ftn.informatika.jpa.model.*;
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

    @Autowired
    private final TokenUtils tokenUtils;

    //dodala
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

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

    @PutMapping (value = "/{id}/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
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

    @GetMapping(value = "/{id}/resetPassword", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
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

    @PutMapping (value = "/{id}/resetPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
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

    @GetMapping(value = "/{id}/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getUsers(Pageable page) {

        Page<User> users = userService.findAll(page);
        int size = userService.getAll().size();
        List<ResponseUserWithIdDTO> responseUserDTOS = new ArrayList<>();
        for(User u: users){
            responseUserDTOS.add(u.parseToResponseUserWithId());
        }
        return new ResponseEntity<>(new ResponsePageDTO(size, Arrays.asList(responseUserDTOS.toArray())), HttpStatus.OK);
    }

    @GetMapping(value = "/{email}/resetPasswordByEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) throws MessagingException, UnsupportedEncodingException {

        if(!userService.findByEmail(email).isPresent()){
            return new ResponseEntity<>(new MessageDTO("User my this email does not exist"), HttpStatus.NOT_FOUND);
        }

        User user = userService.findByEmail(email).get();

        String token = String.valueOf(new Random().nextInt(900000) + 100000);
        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpiration(LocalDateTime.now().plusMinutes(10));

        mailService.sendMail("filipvuksan.iphone@gmail.com", token);
        userService.add(user);

        return new ResponseEntity<>("Email with reset code has been sent!", HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody RequestLoginDTO login) {
        try{
            User user = this.userService.findByEmail(login.getEmail()).get();
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

    @GetMapping(value = "/logout")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return null;
    }

    @PutMapping(value = "/{id}/block")
    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PutMapping(value = "/{id}/unblock")
    @PreAuthorize("hasAuthority('ADMIN')")
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

    @GetMapping(value = "/{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
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

    //TODO CHECK IF RIDE IS VALID
    //RADI
    @PostMapping(value = "/{id}/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> sendMessageToUser(@PathVariable("id") String id, @Valid @RequestBody RequestMessageDTO requestMessageDTO){

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!userService.getUser(id).isPresent()) {
            return new ResponseEntity<>(new MessageDTO("Receiver does not exist!"), HttpStatus.NOT_FOUND);
        }

        User receiver = userService.getUser(id).get();

//        Integer idOfSender = this.userRequestValidation.getUserId(headers);

        String senderId = "2"; // kasnije zaminiti sa idofSender
        if (!userService.getUser(id).isPresent()) {
            return new ResponseEntity<>(new MessageDTO("User does not exist!"), HttpStatus.NOT_FOUND);
        }

        User sender = userService.getUser(senderId).get();

        if (!rideService.getRide(requestMessageDTO.getRideId().toString()).isPresent()) {
            return new ResponseEntity<>("Ride does not exist!", HttpStatus.NOT_FOUND);
        }

        Message message = requestMessageDTO.parseToMessage(sender, receiver);
        this.messageService.add(message);

        return new ResponseEntity<>(message.parseToResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/note", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createNote(@PathVariable("id") String id, @Valid @RequestBody RequestNoteDTO requestNoteDTO){

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

    @GetMapping(value = "/{id}/note", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
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
    public String transferMessages() {
        
        return "LOL";
    }

//    //sa vezbi
//    // REST enpoint
////    @CrossOrigin(origins = "http://localhost:4200")
//    @ExceptionHandler(NullPointerException.class)
//    @RequestMapping(value="/sendMessageRest", method = RequestMethod.POST)
//    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> message) {
//        if (message.containsKey("message")) {
//            if (message.containsKey("toId") && message.get("toId") != null && !message.get("toId").equals("")) {
//                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.get("toId"), message);
//                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.get("fromId"), message);
//            } else {
//                this.simpMessagingTemplate.convertAndSend("/socket-publisher", message);
//            }
//            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
//
//    /*
//     * WebSockets endpoint
//     *
//     * Kao sto smo koristili @RequestMapping za RestController, @MessageMapping se koristi za websocket-e
//     *
//     * Poruka ce biti poslata svim klijentima koji su pretplatili na /socket-publisher topic,
//     * a poruka koja im se salje je messageConverted (simpMessagingTemplate.convertAndSend metoda).
//     *
//     * Na ovaj endpoint klijenti salju poruke, ruta na koju klijenti salju poruke je /send/message (parametar @MessageMapping anotacije)
//     *
//     */
//    @ExceptionHandler(NullPointerException.class)
//    @MessageMapping("/send/message")
//    public Map<String, String> broadcastNotification(String message) {
//        Map<String, String> messageConverted = parseMessage(message);
//
//        if (messageConverted != null) {
//            if (messageConverted.containsKey("toId") && messageConverted.get("toId") != null
//                    && !messageConverted.get("toId").equals("")) {
//                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageConverted.get("toId"),
//                        messageConverted);
//                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageConverted.get("fromId"),
//                        messageConverted);
//            } else {
//                this.simpMessagingTemplate.convertAndSend("/socket-publisher", messageConverted);
//            }
//        }
//
//        return messageConverted;
//    }
//
//    @SuppressWarnings("unchecked")
//    private Map<String, String> parseMessage(String message) {
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, String> retVal;
//
//        try {
//            retVal = mapper.readValue(message, Map.class); // parsiranje JSON stringa
//        } catch (IOException e) {
//            retVal = null;
//        }
//
//        return retVal;
//    }

    @GetMapping(value = "/{userId}/report/days", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getReportDays(@PathVariable("userId") String userId, @Valid @RequestBody RequestReportDTO requestReport){

        if(!this.userService.existsById(userId)){
            return new ResponseEntity<>(new MessageDTO("User with this id does not exist!"), HttpStatus.NOT_FOUND);
        }
        User user = this.userService.getUser(userId).get();
        List<Ride> allRides = new ArrayList<>();
        if(this.passengerService.existsById(userId)){
            allRides = this.passengerService.getPassenger(userId).get().getRides();
        }
        if(this.driverService.existsById(userId)){
            allRides = this.driverService.getDriver(userId).get().getRides();
        }
        List<Ride> rides = this.rideService.getUserRidesBetweenDates(allRides, requestReport.getFrom(), requestReport.getTo());
        List<ResponseReportDayDTO> dates = this.rideService.countRidesForDay(rides, requestReport.getFrom(), requestReport.getTo());
        int sum = this.rideService.getSumReport(dates);
        float average = this.rideService.getAverageReport(dates);
        return new ResponseEntity<>(new ResponseReportDTO(sum, average, dates), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/report/kms", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getReportKms(@PathVariable("userId") String userId, @Valid @RequestBody RequestReportDTO requestReport){
        if(!this.userService.existsById(userId)){
            return new ResponseEntity<>(new MessageDTO("User with this id does not exist!"), HttpStatus.NOT_FOUND);
        }
        User user = this.userService.getUser(userId).get();
        List<Ride> allRides = new ArrayList<>();
        if(this.passengerService.existsById(userId)){
            allRides = this.passengerService.getPassenger(userId).get().getRides();
        }
        if(this.driverService.existsById(userId)){
            allRides = this.driverService.getDriver(userId).get().getRides();
        }
        List<Ride> rides = this.rideService.getUserRidesBetweenDates(allRides, requestReport.getFrom(), requestReport.getTo());
        List<ResponseReportDayDTO> dates = this.rideService.countKmsForDay(rides, requestReport.getFrom(), requestReport.getTo());
        int sum = this.rideService.getSumReport(dates);
        float average = this.rideService.getAverageReport(dates);
        return new ResponseEntity<>(new ResponseReportDTO(sum, average, dates), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/report/money", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getReportMoney(@PathVariable("userId") String userId, @Valid @RequestBody RequestReportDTO requestReport) {
        if (!this.userService.existsById(userId)) {
            return new ResponseEntity<>(new MessageDTO("User with this id does not exist!"), HttpStatus.NOT_FOUND);
        }
        User user = this.userService.getUser(userId).get();
        List<Ride> allRides = new ArrayList<>();
        if (this.passengerService.existsById(userId)) {
            allRides = this.passengerService.getPassenger(userId).get().getRides();
        }
        if (this.driverService.existsById(userId)) {
            allRides = this.driverService.getDriver(userId).get().getRides();
        }
        List<Ride> rides = this.rideService.getUserRidesBetweenDates(allRides, requestReport.getFrom(), requestReport.getTo());
        List<ResponseReportDayDTO> dates = this.rideService.countMoneyForDay(rides, requestReport.getFrom(), requestReport.getTo());
        int sum = this.rideService.getSumReport(dates);
        float average = this.rideService.getAverageReport(dates);
        return new ResponseEntity<>(new ResponseReportDTO(sum, average, dates), HttpStatus.OK);
    }

    @GetMapping(value = "/admin-report/passengers/days", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAdminReportPassengersDays(@RequestBody @Valid RequestReportDTO requestReport){
        List<Passenger> allPassengers = this.passengerService.getAll();
        List<Ride> allRides = this.rideService.getAll();
        List<Ride> passengersDrives = new ArrayList<>();
        for(Passenger p : allPassengers){
            for(Ride r : allRides){
                for(Passenger rp : r.getPassengers()){
                    if(rp.getId() == p.getId()){
                        passengersDrives.add(r);
                    }
                }
            }
        }
        List<Ride> rides = this.rideService.getUserRidesBetweenDates(passengersDrives, requestReport.getFrom(), requestReport.getTo());
        List<ResponseReportDayDTO> dates = this.rideService.countRidesForDay(rides, requestReport.getFrom(), requestReport.getTo());
        int sum = this.rideService.getSumReport(dates);
        float average = this.rideService.getAverageReport(dates);
        return new ResponseEntity<>(new ResponseReportDTO(sum, average, dates), HttpStatus.OK);
    }

    @GetMapping(value = "/admin-report/passengers/kms", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAdminReportPassengersKms(@Valid @RequestBody RequestReportDTO requestReport){
        List<Passenger> allPassengers = this.passengerService.getAll();
        List<Ride> allRides = this.rideService.getAll();
        List<Ride> passengersDrives = new ArrayList<>();
        for(Passenger p : allPassengers){
            for(Ride r : allRides){
                for(Passenger rp : r.getPassengers()){
                    if(rp.getId() == p.getId()){
                        passengersDrives.add(r);
                    }
                }
            }
        }
        List<Ride> rides = this.rideService.getUserRidesBetweenDates(passengersDrives, requestReport.getFrom(), requestReport.getTo());
        List<ResponseReportDayDTO> dates = this.rideService.countKmsForDay(rides, requestReport.getFrom(), requestReport.getTo());
        int sum = this.rideService.getSumReport(dates);
        float average = this.rideService.getAverageReport(dates);
        return new ResponseEntity<>(new ResponseReportDTO(sum, average, dates), HttpStatus.OK);
    }

    @GetMapping(value = "/admin-report/passengers/money", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAdminReportPassengersMoney(@Valid @RequestBody RequestReportDTO requestReport) {
        List<Passenger> allPassengers = this.passengerService.getAll();
        List<Ride> allRides = this.rideService.getAll();
        List<Ride> passengersDrives = new ArrayList<>();
        for(Passenger p : allPassengers){
            for(Ride r : allRides){
                for(Passenger rp : r.getPassengers()){
                    if(rp.getId() == p.getId()){
                        passengersDrives.add(r);
                    }
                }
            }
        }
        List<Ride> rides = this.rideService.getUserRidesBetweenDates(passengersDrives, requestReport.getFrom(), requestReport.getTo());
        List<ResponseReportDayDTO> dates = this.rideService.countMoneyForDay(rides, requestReport.getFrom(), requestReport.getTo());
        int sum = this.rideService.getSumReport(dates);
        float average = this.rideService.getAverageReport(dates);
        return new ResponseEntity<>(new ResponseReportDTO(sum, average, dates), HttpStatus.OK);
    }

    @GetMapping(value = "/admin-report/drivers/days", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAdminReportDriversDays(@Valid @RequestBody RequestReportDTO requestReport){
        List<Driver> allDrivers = this.driverService.getAll();
        List<Ride> allRides = this.rideService.getAll();
        List<Ride> driversRides = new ArrayList<>();
        for(Ride r : allRides){
            for(Driver d : allDrivers){
                if(r.getDriver().getId() == d.getId()){
                    driversRides.add(r);
                }
            }
        }
        List<Ride> rides = this.rideService.getUserRidesBetweenDates(driversRides, requestReport.getFrom(), requestReport.getTo());
        List<ResponseReportDayDTO> dates = this.rideService.countRidesForDay(rides, requestReport.getFrom(), requestReport.getTo());
        int sum = this.rideService.getSumReport(dates);
        float average = this.rideService.getAverageReport(dates);
        return new ResponseEntity<>(new ResponseReportDTO(sum, average, dates), HttpStatus.OK);
    }

    @GetMapping(value = "/admin-report/drivers/kms", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAdminReportDriversKms(@Valid @RequestBody RequestReportDTO requestReport){
        List<Driver> allDrivers = this.driverService.getAll();
        List<Ride> allRides = this.rideService.getAll();
        List<Ride> driversRides = new ArrayList<>();
        for(Ride r : allRides){
            for(Driver d : allDrivers){
                if(r.getDriver().getId() == d.getId()){
                    driversRides.add(r);
                }
            }
        }
        List<Ride> rides = this.rideService.getUserRidesBetweenDates(driversRides, requestReport.getFrom(), requestReport.getTo());
        List<ResponseReportDayDTO> dates = this.rideService.countKmsForDay(rides, requestReport.getFrom(), requestReport.getTo());
        int sum = this.rideService.getSumReport(dates);
        float average = this.rideService.getAverageReport(dates);
        return new ResponseEntity<>(new ResponseReportDTO(sum, average, dates), HttpStatus.OK);
    }

    @GetMapping(value = "/admin-report/drivers/money", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAdminReportDriversMoney(@Valid @RequestBody RequestReportDTO requestReport) {
        List<Driver> allDrivers = this.driverService.getAll();
        List<Ride> allRides = this.rideService.getAll();
        List<Ride> driversRides = new ArrayList<>();
        for(Ride r : allRides){
            for(Driver d : allDrivers){
                if(r.getDriver().getId() == d.getId()){
                    driversRides.add(r);
                }
            }
        }
        List<Ride> rides = this.rideService.getUserRidesBetweenDates(driversRides, requestReport.getFrom(), requestReport.getTo());
        List<ResponseReportDayDTO> dates = this.rideService.countMoneyForDay(rides, requestReport.getFrom(), requestReport.getTo());
        int sum = this.rideService.getSumReport(dates);
        float average = this.rideService.getAverageReport(dates);
        return new ResponseEntity<>(new ResponseReportDTO(sum, average, dates), HttpStatus.OK);
    }
}
