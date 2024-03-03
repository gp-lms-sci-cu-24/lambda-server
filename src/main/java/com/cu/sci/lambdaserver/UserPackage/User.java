package com.cu.sci.lambdaserver.UserPackage;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
@PrimaryKeyJoinColumn(name = "user_id")
public class User {
    @Id
    private String userId;
    private String password;

    private String firstName;
    private String lastName;
    private String email;
    private String nationalId;
    private String photoUrl;
    private int age;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
}
