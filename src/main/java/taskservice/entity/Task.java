package taskservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import taskservice.constant.Limits;

@Entity
@Table(name = "tasks",indexes = {
        @Index(name = "idx_tasks_assignee",columnList = "assignee_id"),
        @Index(name = "idx_tasks_status",columnList = "status")
})
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = Limits.DESCRIPTION_MAX)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.TODO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;
}
