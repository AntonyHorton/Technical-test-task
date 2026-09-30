package taskservice.dto;

import jakarta.validation.constraints.NotNull;
import taskservice.entity.TaskStatus;

public record ChangeStatusRequest(
        @NotNull
        TaskStatus status
) {}
