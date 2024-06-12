package com.cu.sci.lambdaserver.student.dto;


import com.cu.sci.lambdaserver.utils.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentDto {
    
    @NotNull(message = "first name cannot be null")
    private String firstName;

    @NotNull(message = "father name cannot be null")
    private String fatherName;

    @NotNull(message = "grand father name cannot be null")
    private String grandfatherName;

    @NotNull(message = "last name cannot be null")
    private String lastname;

    @NotNull(message = "address cannot be null")
    private String address;

    @JsonProperty("joining_year")
    private String joiningYear;

    @JsonProperty("department_code")
    private String departmentCode;

    @NotNull
    private Gender gender;

}
