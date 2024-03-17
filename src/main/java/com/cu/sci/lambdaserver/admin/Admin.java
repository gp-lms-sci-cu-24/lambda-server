package com.cu.sci.lambdaserver.admin;

import com.cu.sci.lambdaserver.user.Role;
import com.cu.sci.lambdaserver.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "admins")
public class Admin extends User {
    @Column(unique = true)
    private String adminId;
    private String departmentId;
//    public Admin(String userId, String password, String firstName, String lastName, String email,
//                 String phoneNumber, String nationalId, String profilePictureUrl, Integer age, Role role,
//                 String adminId, Boolean isLocked, Boolean isActive, String departmentId) {
//        super(userId, password, firstName, lastName, email, phoneNumber, nationalId,
//                profilePictureUrl, age, role);
//        this.adminId = adminId;
//        this.isLocked = isLocked;
//        this.isActive = isActive;
//        this.departmentId = departmentId;
//        assert this.getRole() == Role.ADMIN;
//    }
//    public Admin(User user, String adminId, Boolean isLocked, Boolean isActive, String departmentId) {
//        super(user.getUserId(), user.getPassword(), user.getFirstName(), user.getLastName(),
//                user.getEmail(), user.getPhoneNumber(), user.getNationalId(), user.getProfilePictureUrl(),
//                user.getAge(), user.getRole());
//        this.adminId = adminId;
//        this.isLocked = isLocked;
//        this.isActive = isActive;
//        this.departmentId = departmentId;
//        assert this.getRole() == Role.ADMIN;
//    }

}
