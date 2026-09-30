package taskservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import taskservice.constant.PageSize;
import taskservice.dto.AssignTaskRequest;
import taskservice.dto.ChangeStatusRequest;
import taskservice.dto.CreateTaskRequest;
import taskservice.dto.TaskDto;
import taskservice.service.TaskService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public Page<TaskDto> getTasks(
            @PageableDefault (size = PageSize.MAX_PAGE_SIZE,sort = "id")
            Pageable pageable) {
        return taskService.getTasks(pageable);
    }

    @GetMapping("/{id}")
    public TaskDto getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(
            @Valid
            @RequestBody
            CreateTaskRequest taskRequest) {
        return taskService.createTask(taskRequest);
    }

    @PutMapping("{id}/assignee")
    public TaskDto assignTask(
            @PathVariable Long id,
            @Valid
            @RequestBody
            AssignTaskRequest assignTaskRequest
            ){
        return taskService.assignTask(id,assignTaskRequest.assigneeId());
    }

    @PatchMapping("/{id}/status")
    public TaskDto changeTaskStatus(
            @PathVariable Long id,
            @Valid
            @RequestBody ChangeStatusRequest changeStatusRequest) {
        return taskService.changeStatus(id,changeStatusRequest.status());
    }
}
