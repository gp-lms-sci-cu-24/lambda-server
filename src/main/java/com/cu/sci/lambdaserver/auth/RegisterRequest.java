package com.cu.sci.lambdaserver.auth;

import com.cu.sci.lambdaserver.UserPackage.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String nationalId;
    private int age;


}
