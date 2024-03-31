package com.cu.sci.lambdaserver.professor.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDto {
    public interface UpdateValidation {}
    public interface CreateValidation {}
    public interface NoValidation {}

    @NotNull(groups = {UpdateValidation.class}, message = "Location ID cannot be null during update")
    private Long professorId;
    @NotNull(groups = {CreateValidation.class}, message = "User ID cannot be null during creation")
    private Long userId;
}
