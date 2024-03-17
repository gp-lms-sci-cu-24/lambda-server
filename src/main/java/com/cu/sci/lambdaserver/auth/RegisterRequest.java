package com.cu.sci.lambdaserver.auth;

import com.cu.sci.lambdaserver.user.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @NotEmpty
    @Size(min = 7,max = 7 ,message = "the id must be 7 numbers")
    private String id;
    @NotNull
    @NotEmpty(message = "first name must be not empty")
    private String firstname;
    @NotNull
    @NotEmpty(message = "last name must be not empty")
    private String lastname;
    @NotNull
    @NotEmpty(message = "last name must be not empty")
    private String email;
    @Size(min = 7, message = "password must be >= 7 ")
    private String password;
    @NotNull
    private Role role;
}
