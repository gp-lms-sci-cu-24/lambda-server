package com.cu.sci.lambdaserver.contactinfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateContactInfoDto {

    @NotBlank(message = "phone cannot be null")
    @Length(min = 11, max = 11, message = "phone must be 10 digits")
    private String phone;

    @NotBlank(message = "telephone cannot be null")
    @Length(min = 10, max = 10, message = "telephone must be 10 digits")
    private String telephone;

    @NotBlank(message = "email cannot be null")
    @Email(message = "email must be valid")
    private String email;

}
