package taskservice.dto;

import taskservice.entity.TaskStatus;

public record TaskDto(
        Long id,
        String title,
        String description,
        TaskStatus status,
        UserDto assignee
) {}
