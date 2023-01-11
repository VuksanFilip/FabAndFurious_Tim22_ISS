package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Admin;
import rs.ac.uns.ftn.informatika.jpa.repository.AdminRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IAdminService;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements IAdminService {

    private AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAll() {
        return (List<Admin>) this.adminRepository.findAll();
    }

    @Override
    public Optional<Admin> getAdmin(String id) {
        return  this.adminRepository.findById(Long.parseLong(id));
    }

    public void add(Admin admin) {
        this.adminRepository.save(admin);
    }
}
