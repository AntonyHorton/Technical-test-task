package taskservice.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String TASK_CREATED_TOPIC = "task_created";
    public static final String TASK_ASSIGNED_TOPIC = "task_assigned";

    @Bean
    public NewTopic taskCreatedTopic() {
        return TopicBuilder.name(TASK_CREATED_TOPIC)
                .partitions(3)
                .replicas(1) //because we have one broker
                .build();
    }

    @Bean
    public NewTopic taskAssignedTopic() {
        return TopicBuilder.name(TASK_ASSIGNED_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
