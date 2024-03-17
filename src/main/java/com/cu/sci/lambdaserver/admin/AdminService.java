package com.cu.sci.lambdaserver.admin;

import com.cu.sci.lambdaserver.user.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // Create a new admin
    public Admin saveAdmin(Admin admin){
        try {
            return adminRepository.save(admin);
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to save admin: " + e.getMessage());
        }
    }
    public List<Admin> fetchAllAdmins() {
        try {
            return (List<Admin>) adminRepository.findAll();
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to fetch all admins: " + e.getMessage() );
        }
    }

    public Optional<Admin> fetchAdminById(String id) {
        try {
            return adminRepository.findById(id);
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to fetch admin by ID: " + e.getMessage());
        }
    }
    public boolean deleteAdmin(String id) {
        try {
            adminRepository.deleteById(id);
            return true; // Deletion successful
        } catch (Exception e) {
            // Handle exception or log the error
            throw new RuntimeException("Failed to delete admin: " + e.getMessage());
        }
    }
    @PostConstruct
    public void seedAdmins() {
//        Admin admin1 = new Admin("admin1", "adminpass1", "Admin", "One", "admin1@example.com",
//                "1234567890", "1234567890123", "https://example.com/admin1.jpg", 35, Role.ADMIN,
//                "admin_id_1", false, true, "dept_id_1");
//
//        Admin admin2 = new Admin("admin2", "adminpass2", "Admin", "Two", "admin2@example.com",
//                "9876543210", "9876543210987", "https://example.com/admin2.jpg", 40, Role.ADMIN,
//                "admin_id_2", true, true, "dept_id_2");
//
//        adminRepository.save(admin1);
//        adminRepository.save(admin2);
    }
}
