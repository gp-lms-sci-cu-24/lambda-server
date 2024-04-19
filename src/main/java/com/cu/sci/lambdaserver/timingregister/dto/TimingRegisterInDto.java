package com.cu.sci.lambdaserver.timingregister.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimingRegisterInDto {
    @NotNull(message = "Course register ID is required", groups = {UpdateValidation.class})
    private Long timingRegisterId;
    @NotNull(message = "Course class ID is required", groups = {CreateValidation.class})
    @Null(message = "Course class ID must be null during update", groups = {UpdateValidation.class})
    private Long courseClassId;
    @NotNull(message = "course class timing ID is required", groups = {CreateValidation.class})
    @Null(message = "course class timing ID must be null during update", groups = {UpdateValidation.class})
    private Long courseClassTimingId;

    public interface UpdateValidation {
    }

    public interface CreateValidation {
    }

}
