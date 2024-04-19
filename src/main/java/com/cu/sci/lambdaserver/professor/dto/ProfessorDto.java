package com.cu.sci.lambdaserver.professor.dto;

import com.cu.sci.lambdaserver.user.dto.UserDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDto extends UserDto {


}
