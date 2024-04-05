package com.cu.sci.lambdaserver.professor.dto;

import jakarta.persistence.Column;
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
    @NotNull(groups = {UpdateValidation.class, CreateValidation.class}, message = "User ID cannot be null during creation")
    private Long id;
    @NotNull(groups = {CreateValidation.class}, message = "Username cannot be null during creation")
    private String username;
    @NotNull(groups = {CreateValidation.class}, message = "Password cannot be null during creation")
    private String password;

    private String profilePicture;
}
