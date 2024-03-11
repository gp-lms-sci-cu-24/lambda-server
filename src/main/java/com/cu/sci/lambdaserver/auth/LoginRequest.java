package com.cu.sci.lambdaserver.auth;

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
public class LoginRequest {
    @NotNull
    @NotEmpty
    @Size(min = 7,max = 7 ,message = "the id must be 7 numbers")
    private String id;
    @NotNull
    @NotEmpty
    @Size(min = 7, message = "password must be >= 7 ")
    private String password;

}
