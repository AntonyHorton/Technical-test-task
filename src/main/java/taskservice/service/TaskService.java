package taskservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import taskservice.dto.CreateTaskRequest;
import taskservice.dto.TaskDto;
import taskservice.dto.UserDto;
import taskservice.entity.Task;
import taskservice.entity.TaskStatus;
import taskservice.entity.User;
import taskservice.exception.NotFoundException;
import taskservice.kafka.publisher.TaskEventPublisher;
import taskservice.repository.TaskRepository;
import taskservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final TaskEventPublisher taskEventPublisher;

    private TaskDto toDto(Task task) {
        User user = task.getAssignee();
        UserDto assignee = user == null ? null : new UserDto(
                user.getId(), user.getName(), user.getEmail()
        );
        return new TaskDto(task.getId(), task.getTitle(),
                task.getDescription(), task.getStatus(), assignee);
    }

    @Transactional(readOnly = true)
    public Page<TaskDto> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public TaskDto getTask(Long id) {
        return taskRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(()-> new NotFoundException("Task not found with id " + id));
    }

    @Transactional
    public TaskDto createTask(CreateTaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        Task savedTask = taskRepository.save(task);
        taskEventPublisher.taskCreated(savedTask);
        return toDto(savedTask);
    }

    @Transactional
    public TaskDto assignTask(Long taskId, Long assigneeId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new NotFoundException("Task not found with id " + taskId));
        User user = userRepository.findById(assigneeId)
                .orElseThrow(()-> new NotFoundException("User not found with id " + assigneeId));
        task.setAssignee(user);
        taskEventPublisher.taskAssigned(task, user);
        return toDto(task);
    }

    @Transactional
    public TaskDto changeStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new NotFoundException("Task not found with id " + taskId));
        task.setStatus(status);
        return toDto(task);
    }
}
