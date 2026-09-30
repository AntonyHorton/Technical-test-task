package taskservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import taskservice.constant.Limits;

public record CreateTaskRequest(
        @NotBlank
        @Size(max = 255)
        String title,
        @Size(max = Limits.DESCRIPTION_MAX)
        String description
) {}
