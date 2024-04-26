package com.cu.sci.lambdaserver.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

@SuperBuilder
@Data
@NoArgsConstructor
public class CreateUserRequestDto {
    @NotNull(message = "Username cannot be null.")
    @NotBlank(message = "Username cannot be Empty.")
    @Length(min = 3, message = "Username can't be less than 3 characters.")
    @Length(max = 50, message = "Username Can't be more than 50 characters.")
    @Pattern(regexp = "[A-Za-z0-9_.-]*", message = "Invalid username : username must contains Letters, numbers, dashes, underscores or dots only.")
    private String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be Empty")
    @Length(min = 3, message = "Password can't be less than 3 characters.")
    @Length(max = 50, message = "Password Can't be more than 50 characters.")
    private String password;

}
