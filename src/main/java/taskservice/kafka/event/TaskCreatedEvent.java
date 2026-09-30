package taskservice.kafka.event;

import java.time.Instant;

public record TaskCreatedEvent(
        Long taskId,
        String title,
        Instant createdAt
) {}
