package taskservice.kafka.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import taskservice.entity.Task;
import taskservice.entity.User;
import taskservice.kafka.event.TaskAssignedEvent;
import taskservice.kafka.event.TaskCreatedEvent;
import taskservice.kafka.topic.KafkaTopicConfig;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class TaskEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void taskCreated(Task task) {
        var event = new TaskCreatedEvent(task.getId(),task.getTitle(), Instant.now());
        kafkaTemplate.send(KafkaTopicConfig.TASK_CREATED_TOPIC, String.valueOf(task.getId()), event);
    }

    public void taskAssigned(Task task, User assignee) {
        var event = new TaskAssignedEvent(task.getId(),assignee.getId(), Instant.now());
        kafkaTemplate.send(KafkaTopicConfig.TASK_ASSIGNED_TOPIC, String.valueOf(task.getId()), event);
    }
}
