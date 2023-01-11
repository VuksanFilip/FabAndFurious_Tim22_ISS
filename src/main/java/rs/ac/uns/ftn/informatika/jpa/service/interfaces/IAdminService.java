package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Admin;

import java.util.List;
import java.util.Optional;

public interface IAdminService {

    List<Admin> getAll();

    Optional<Admin> getAdmin(String id);

    void add(Admin admin);
}
