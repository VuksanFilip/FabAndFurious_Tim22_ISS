package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.model.Role;
import rs.ac.uns.ftn.informatika.jpa.repository.RoleRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserService;

@RestController
@RequestMapping("/api/role/")
public class RoleController {

    RoleRepository roleService;
    IUserService userService;

    RoleController(RoleRepository roleService, IUserService userService){
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> addRole() {

        Role adminRole = new Role("Admin");
        Role passengerRole = new Role("Passenger");
        Role driverRole = new Role("Driver");
        roleService.save(adminRole);
        roleService.save(passengerRole);
        roleService.save(driverRole);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
