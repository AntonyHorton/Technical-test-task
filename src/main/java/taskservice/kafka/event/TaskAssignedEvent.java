package taskservice.kafka.event;

import java.time.Instant;

public record TaskAssignedEvent(
        Long taskId,
        Long assigneeId,
        Instant assignedAt
) {}
