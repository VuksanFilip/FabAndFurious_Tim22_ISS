package rs.ac.uns.ftn.informatika.jpa.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.informatika.jpa.dto.messages.MessageDTO;
import rs.ac.uns.ftn.informatika.jpa.dto.request.*;
import rs.ac.uns.ftn.informatika.jpa.dto.response.*;
import rs.ac.uns.ftn.informatika.jpa.model.*;
import rs.ac.uns.ftn.informatika.jpa.model.enums.Role;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverController {

    private final IDriverService driverService;
    private final IDocumentService documentService;
    private final IVehicleService vehicleService;
    private final IWorkingHourService workHourService;
    private final IRideService rideService;
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final IDriverEditService driverEditService;


    public DriverController(IDriverService driverService, IDocumentService documentService, IVehicleService vehicleService, IWorkingHourService workHourService, IRideService rideService, IUserService userService, PasswordEncoder passwordEncoder, IDriverEditService driverEditService) {
        this.driverService = driverService;
        this.documentService = documentService;
        this.vehicleService = vehicleService;
        this.workHourService = workHourService;
        this.rideService = rideService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.driverEditService = driverEditService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createNewDriver(@Valid @RequestBody RequestDriverDTO requestDriverDTO) {

        if (this.userService.findByEmail(requestDriverDTO.getEmail()) != null) {
            return new ResponseEntity<>(new MessageDTO("User with that email already exists!"), HttpStatus.BAD_REQUEST);
        }

        Driver driver = requestDriverDTO.parseToDriver();
        driver.setPassword(passwordEncoder.encode(requestDriverDTO.getPassword()));
        driver.setRole(Role.DRIVER);

        driverService.add(driver);

        return new ResponseEntity<>(driver.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponsePageDTO> getAllDrivers(Pageable page) {

        Page<Driver> drivers = driverService.findAll(page);
        int results = driverService.getAll().size();

        List<ResponseDriverDTO> responseDriverDTOS = new ArrayList<>();
        for (Driver d : drivers) {
            responseDriverDTOS.add(new ResponseDriverDTO(d));
        }

        return new ResponseEntity<>(new ResponsePageDTO(results, Arrays.asList(responseDriverDTOS.toArray())), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getDriver(@PathVariable("id") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!this.driverService.getDriver(id).isPresent()) {
            return new ResponseEntity<>(new MessageDTO("Driver does not exist"), HttpStatus.NOT_FOUND);
        }

        Driver driver = this.driverService.getDriver(id).get();

        return new ResponseEntity<>(driver.parseToResponse(), HttpStatus.OK);
    }

    @PostMapping(value="/{driverId}/request-edit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   // @PreAuthorize("hasAuthority('DRIVER')")
    public ResponseEntity<?> sendRequestForEdit(@PathVariable("driverId") String driverId, @Valid @RequestBody RequestEditDriverDTO requestDriverDTO){
        if(!this.driverService.getDriver(driverId).isPresent()){
            return new ResponseEntity<>(new MessageDTO("Driver with this id does not exist!"), HttpStatus.OK);
        }
        DriverEdit driverEdit = new DriverEdit(requestDriverDTO.getName(), requestDriverDTO.getSurname(), requestDriverDTO.getProfilePicture(), requestDriverDTO.getTelephoneNumber(), requestDriverDTO.getEmail(), requestDriverDTO.getAddress());
        this.driverEditService.add(driverEdit);
        Driver driver = this.driverService.getDriver(driverId).get();
        driver.setEdit(driverEdit);
        this.driverService.add(driver);
        return new ResponseEntity<>(new MessageDTO("Successfully sent request for updating driver!"), HttpStatus.OK);
    }

    @PutMapping (value = "update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  //  @PreAuthorize("hasAnyAuthority('ADMIN', 'PASSENGER')")
    public ResponseEntity<?> updateDriver2(@PathVariable("id") String id, @Valid @RequestBody RequestDriverUpdateDTO requestDriverUpdateDTO) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if(!this.driverService.getDriver(id).isPresent()){
            return new ResponseEntity<>("Driver does not exist!", HttpStatus.NOT_FOUND);
        }
        Driver driverForUpdate = driverService.getDriver(id).get();
        driverForUpdate.update2(requestDriverUpdateDTO);
        driverService.add(driverForUpdate);
        return new ResponseEntity<>(driverForUpdate.parseToResponse(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateDriver(@PathVariable("id") String id, @Valid @RequestBody RequestEditApprovalDTO approval) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.BAD_REQUEST);
        }
        if (!this.driverService.getDriver(id).isPresent()) {
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        if(this.driverService.getDriver(id).get().getEdit() == null){
            return new ResponseEntity<>(new MessageDTO("This driver does not have request for update!"), HttpStatus.BAD_REQUEST);
        }

        if(approval.isApproval()){
            Driver driverForUpdate = driverService.getDriver(id).get();
            driverForUpdate.update();
            driverForUpdate.setEdit(null);
            driverService.add(driverForUpdate);
            return new ResponseEntity<>(new MessageDTO("Successfully updated driver!"), HttpStatus.OK);
        }
        Driver driverForUpdate = driverService.getDriver(id).get();
        driverForUpdate.setEdit(null);
        driverService.add(driverForUpdate);
        return new ResponseEntity<>(new MessageDTO("Successfully rejected request for updating driver!"), HttpStatus.OK);
    }

    @GetMapping(value = "/edit-requests", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getAllEditRequests(){
        List<ResponseEditRequestDTO> allRequestsList = new ArrayList<>();
        ResponseAllRequestsDTO allRequests = new ResponseAllRequestsDTO();
        for(Driver d : this.driverService.getAll()){
            if(d.getEdit() != null){
                allRequestsList.add(new ResponseEditRequestDTO(d.getId().toString(), d.getEdit().parseToDTO()));
            }
        }
        allRequests.setAllRequests(allRequestsList);
        return new ResponseEntity<>(allRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getDriverDocuments(@PathVariable("id") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!this.driverService.getDriver(id).isPresent()) {
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }

        Driver driver = driverService.getDriver(id).get();
        List<Document> driverDocuments = driver.getDocuments();

        List<ResponseDriverDocumentDTO> driverDocumentDTOS = new ArrayList<>();
        for (Document d : driverDocuments) {
            driverDocumentDTOS.add(new ResponseDriverDocumentDTO(d));
        }

        return new ResponseEntity<>(driverDocumentDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/documents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addDriverDocument(@PathVariable("id") String id, @Valid @RequestBody RequestDriverDocumentDTO requestDriverDocumentDTO) throws Exception {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!this.driverService.getDriver(id).isPresent()) {
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        Driver driver = driverService.getDriver(id).get();
        Document document = requestDriverDocumentDTO.parseToDocument(driver);
        driver.getDocuments().add(document);

        documentService.add(document);

        return new ResponseEntity<>(document.parseToResponse(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/document/{document-id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteDriverDocument(@PathVariable("document-id") String documentId) {

        if(!StringUtils.isNumeric(documentId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!this.documentService.getDocument(documentId).isPresent()) {
            return new ResponseEntity<>("Document does not exist", HttpStatus.NOT_FOUND);
        }

        Document document = documentService.getDocument(documentId).get();

        documentService.deleteById(documentId);

        return new ResponseEntity<>(new MessageDTO("Driver document deleted successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER', 'PASSENGER')")
    public ResponseEntity<?> getDriverVehicle(@PathVariable("id") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!this.driverService.getDriver(id).isPresent()) {
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }
        if (this.driverService.getDriver(id).get().getVehicle() == null) {
            return new ResponseEntity<>(new MessageDTO("Vehicle is not assigned!"), HttpStatus.BAD_REQUEST);
        }
        Vehicle vehicle = driverService.getDriver(id).get().getVehicle();
        return new ResponseEntity<>(vehicle.parseToResponse(), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addDriverVehicle(@PathVariable("id") String id, @Valid @RequestBody RequestDriverVehicleDTO requestDriverVehicleDTO) throws Exception {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!this.driverService.getDriver(id).isPresent()) {
            return new ResponseEntity<>(new MessageDTO("Driver does not exist"), HttpStatus.NOT_FOUND);
        }
        Driver driver = this.driverService.getDriver(id).get();
        if (driver.getVehicle() != null) {
            return new ResponseEntity<>(new MessageDTO("Driver already has a vehicle"), HttpStatus.NOT_FOUND);
        }

        Vehicle vehicle = requestDriverVehicleDTO.parseToVehicle(driver);
        driver.setVehicle(vehicle);

        this.vehicleService.add(vehicle);
        this.driverService.add(driver);

        return new ResponseEntity<>(vehicle.parseToResponse(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> changeDriverVehicle(@PathVariable("id") String driverId, @Valid @RequestBody RequestDriverVehicleDTO requestDriverVehicleDTO) {

        if(!StringUtils.isNumeric(driverId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!this.driverService.getDriver(driverId).isPresent()) {
            return new ResponseEntity<>(new MessageDTO("Driver does not exist"), HttpStatus.NOT_FOUND);
        }

        Driver driver = this.driverService.getDriver(driverId).get();
        Vehicle currentVehicle = driver.getVehicle();
        Vehicle newVehicle = requestDriverVehicleDTO.parseToVehicle(driver);
        driver.setVehicle(newVehicle);

        this.vehicleService.add(newVehicle);
        this.driverService.add(driver);
        this.vehicleService.deleteById(currentVehicle.getId());

        return new ResponseEntity<>(newVehicle.parseToResponse(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/working-hour", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getDriverWorkingHours(@PathVariable("id") String driverId,
                                                   Pageable page,
                                                   @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from,
                                                   @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to) {

        if(!StringUtils.isNumeric(driverId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!this.driverService.getDriver(driverId).isPresent()) {
            return new ResponseEntity<>(new MessageDTO("Driver does not exist"), HttpStatus.NOT_FOUND);
        }

        Date dateFrom = null;
        Date dateTo = null;

        if (from != null || to != null) {
            if  (from != null) {
                dateFrom = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
            if (to != null) {
                dateTo = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        }

        Page<WorkingHour> workingHours = this.workHourService.findAll(driverId, page, dateFrom, dateTo);
        int size = workingHours.getTotalPages();

        List<ResponseDriverWorkingHourDTO> driverWorkingHourDTOS = new ArrayList<>();
        for(WorkingHour workingHour : workingHours){
            driverWorkingHourDTOS.add(workingHour.parseToResponse());
        }

        return new ResponseEntity<>(new ResponsePageDTO(size, Arrays.asList(driverWorkingHourDTOS.toArray())), HttpStatus.OK);
    }

    //RADI (NADAM SE DA SAM DOBRO ISTESTIRAO, MORA SE ODKOMENTARISATI ONAJ IF)
    @PostMapping(value = "/{id}/working-hour", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('DRIVER')")
    public ResponseEntity<?> createDriverWorkingHour(@PathVariable("id") String driverId, @Valid @RequestBody RequestDriverWorkingHourStartDTO requestWorkingHour) throws Exception {

        if (!this.driverService.getDriver(driverId).isPresent()) {
            return new ResponseEntity<>(new MessageDTO("Driver does not exist"), HttpStatus.NOT_FOUND);
        }
        if (this.driverService.getDriver(driverId).get().getVehicle() == null) {
            return new ResponseEntity<>(new MessageDTO("Cannot start shift because the vehicle is not defined!"), HttpStatus.BAD_REQUEST);
        }
        // Zakomentarisano radi lakseg unosanje u bazu (tj da ne pravi problem oko toga dal je pre lokalnog vremena)
//        if(requestWorkingHour.getStart().isBefore(LocalDateTime.now())){
//            return new ResponseEntity<>(new MessageDTO("Cannot start shift in the past"), HttpStatus.BAD_REQUEST);
//        }
        if (this.workHourService.checkIfShiftBetween(driverId, requestWorkingHour.getStart())) {
            return new ResponseEntity<>(new MessageDTO("Cannot start shift because it was already ongoing in that time!"), HttpStatus.BAD_REQUEST);
        }
        if (this.workHourService.checkIfShiftOngoing(driverId)) {
            return new ResponseEntity<>(new MessageDTO("Cannot start shift because it is already ongoing!"), HttpStatus.BAD_REQUEST);
        }
        if (this.workHourService.checkIfPassed8hLimit(driverId, requestWorkingHour.getStart())) {
            return new ResponseEntity<>(new MessageDTO("Cannot start shift because it passsed 8 hours limit!"), HttpStatus.BAD_REQUEST);
        }
        Driver driver = this.driverService.getDriver(driverId).get();
        WorkingHour workingHour = new WorkingHour(driver, requestWorkingHour);
        this.workHourService.add(workingHour);
        return new ResponseEntity<>(workingHour.parseToResponse(), HttpStatus.OK);
    }

    // from= yyyy-MM-ddThh:mm:ss -> 2000-01-31T09:00:00
    // to= yyyy-MM-ddThh:mm:ss -> 2000-01-31T09:00:00

    @GetMapping(value = "/{id}/ride")
  //  @PreAuthorize("hasAnyAuthority('ADMIN', 'DRIVER')")
    public ResponseEntity<?> getDriverRides(
            @PathVariable("id") String driverId,
            Pageable page,
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate to) {

        if (!this.driverService.getDriver(driverId).isPresent()) {
            return new ResponseEntity<>("Driver does not exist", HttpStatus.NOT_FOUND);
        }

        Date dateFrom = null;
        Date dateTo = null;

        if (from != null || to != null) {
            if  (from != null) {
                dateFrom = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
            if (to != null) {
                dateTo = Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        }

        Page<Ride> driversRides = this.rideService.findAll(driverId, page, dateFrom, dateTo);
        List<ResponseRideNoStatusDTO> driverRidesList = new ArrayList<>();

        for(Ride driverRide : driversRides){
            driverRidesList.add(driverRide.parseToResponseNoStatus());
        }
        return new ResponseEntity<>(new ResponsePageDTO(driverRidesList.size(), Arrays.asList(driverRidesList.toArray())), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/rides", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'PASSENGER')")
    public ResponseEntity<?> getDriverRides(@PathVariable("id") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        List<ResponseRideNoStatusDTO> responseRideDTOS = rideService.getResponseRideDriver(id);

        return new ResponseEntity<>(responseRideDTOS, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}/pendingrides", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'PASSENGER')")
    public ResponseEntity<?> getDriverPendingRides(@PathVariable("id") String id) {

        if(!StringUtils.isNumeric(id)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        List<ResponseRideNoStatusDTO> responseRideDTOS = rideService.getResponseRidePendingDriver(id);

        return new ResponseEntity<>(responseRideDTOS, HttpStatus.OK);
    }


    //RADI
    @GetMapping(value = "/working-hour/{working-hour-id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('DRIVER')")
    public ResponseEntity<?> getWorkingHour(@PathVariable("working-hour-id") String workingHourId) {

        if(!StringUtils.isNumeric(workingHourId)){
            return new ResponseEntity<>(new MessageDTO("Id is not numeric"), HttpStatus.NOT_FOUND);
        }
        if (!this.workHourService.getWorkHour(workingHourId).isPresent()) {
            return new ResponseEntity<>("Working hour does not exist!", HttpStatus.NOT_FOUND);
        }
        WorkingHour workingHour = this.workHourService.getWorkHour(workingHourId).get();
        return new ResponseEntity<>(workingHour.parseToResponse(), HttpStatus.OK);
    }

    //RADI (NADAM SE DA SAM DOBRO ISTESTIRAO, MORA SE ODKOMENTARISATI ONAJ IF)
    @PutMapping(value = "/working-hour/{working-hour-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @PreAuthorize("hasAuthority('DRIVER')")
    public ResponseEntity<?> updateWorkingHour(@PathVariable("working-hour-id") String workingHourId, @Valid @RequestBody RequestDriverWorkingHourEndDTO requestWorkingHour) {
        if (!this.workHourService.getWorkHour(workingHourId).isPresent()) {
            return new ResponseEntity<>(new MessageDTO("Working hour does not exist!"), HttpStatus.NOT_FOUND);
        }
        if (this.driverService.getDriver(this.workHourService.getWorkHour(workingHourId).get().getDriver().getId().toString()).get().getVehicle() == null) {
            return new ResponseEntity<>(new MessageDTO("Cannot end shift because the vehicle is not defined!"), HttpStatus.BAD_REQUEST);
        }

//         Zakomentarisano radi lakseg unosanje u bazu (tj da ne pravi problem oko toga dal je posle lokalnog vremena)
//        if(requestWorkingHour.getEnd().isAfter(LocalDateTime.now())){
//            return new ResponseEntity<>(new MessageDTO("Cannot end shift in the future"), HttpStatus.BAD_REQUEST);
//        }
        WorkingHour workingHour = this.workHourService.getWorkHour(workingHourId).get();
        System.out.println("aa");
        if(workingHour.getStart().isAfter(requestWorkingHour.getEnd())){
            return new ResponseEntity<>(new MessageDTO("Cannot end before start"), HttpStatus.BAD_REQUEST);
        }
        if (!this.workHourService.checkIfEndIsNull(workingHour.getEndTime())) {
            return new ResponseEntity<>(new MessageDTO("Cannot end shift because it is already finished!"), HttpStatus.BAD_REQUEST);
        }
        workingHour.setEndTime(requestWorkingHour.getEnd());
        this.workHourService.add(workingHour);
        return new ResponseEntity<>(workingHour.parseToResponse(), HttpStatus.OK);
    }

    //TODO PROVERITI DA LI RADI
    /*
    Ova komanda se preporucuje da se svaki minut refreshuje,
    sluzi da proveri one "end-ove" koji su na null duze
    nego sto su radili (npr. u slucaju da je driver zaboravio
    da zavrsi sa radnim vremenom, u poslednja 24 sata mu se
    trazi ukupno radno vreme i nakon prekoracenog vremena
    automatcki enduje u tih 24 sata
     */
    @PutMapping(value = "/working-hour/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('DRIVER')")
    public ResponseEntity<?> updateWorkingHour() {
        this.workHourService.refreshUnfinishedShifts();
        return new ResponseEntity<>(null);
    }
}
