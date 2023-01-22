package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Role;

import java.util.List;

public interface IRoleService {
    Role findById(Long id);
    List<Role> findByName(String name);
}
