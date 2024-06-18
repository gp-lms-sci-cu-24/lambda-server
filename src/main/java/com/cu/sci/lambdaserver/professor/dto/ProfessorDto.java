package com.cu.sci.lambdaserver.professor.dto;

import com.cu.sci.lambdaserver.professor.ProfessorDegree;
import com.cu.sci.lambdaserver.user.dto.UserDto;
import com.cu.sci.lambdaserver.utils.enums.Gender;
import com.cu.sci.lambdaserver.utils.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProfessorDto extends UserDto {

    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;

    private ProfessorDegree degree;

    private Collection<Role> roles;
}
