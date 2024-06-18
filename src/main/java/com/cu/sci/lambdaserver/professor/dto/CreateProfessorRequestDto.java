package com.cu.sci.lambdaserver.professor.dto;

import com.cu.sci.lambdaserver.professor.ProfessorDegree;
import com.cu.sci.lambdaserver.user.dto.CreateUserRequestDto;
import com.cu.sci.lambdaserver.utils.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class CreateProfessorRequestDto extends CreateUserRequestDto {
    @NotNull(message = "First Name cannot be null.")
    private String firstName;

    @NotNull(message = "Last Name cannot be null.")
    private String lastName;

    @NotNull(message = "Email cannot be null.")
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender cannot be null.")
    private Gender gender;

    @NotNull(message = "Degree cannot be null.")
    private ProfessorDegree degree;

}
