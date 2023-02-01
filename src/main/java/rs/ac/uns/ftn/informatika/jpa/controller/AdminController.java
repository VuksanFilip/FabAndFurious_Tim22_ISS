package rs.ac.uns.ftn.informatika.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.informatika.jpa.model.Admin;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IAdminService;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IUserService;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    private final IAdminService adminService;
    private final IUserService userService;

    public AdminController(IAdminService adminService, IUserService userService){
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAdmin() {
        Admin admin = adminService.getAll().get(0);
        return new ResponseEntity<>(admin.parseToResponse(), HttpStatus.OK);
    }

}
