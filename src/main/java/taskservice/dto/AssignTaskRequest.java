package taskservice.dto;

import jakarta.validation.constraints.NotNull;

public record AssignTaskRequest(
        @NotNull
        Long assigneeId
) {}
