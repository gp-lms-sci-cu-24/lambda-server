package com.cu.sci.lambdaserver.professor.dto;

import com.cu.sci.lambdaserver.professor.ProfessorDegree;
import com.cu.sci.lambdaserver.user.dto.UserDto;
import com.cu.sci.lambdaserver.utils.enums.Gender;
import com.cu.sci.lambdaserver.utils.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ProfessorDto extends UserDto {

    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;

    private ProfessorDegree degree;

    private Collection<Role> roles;
}
