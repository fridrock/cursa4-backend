package ru.fridrock.jir_backend.tasks;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fridrock.jir_backend.tasks.dto.AiCreateTaskDto;
import ru.fridrock.jir_backend.tasks.dto.TaskDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskDto create(@Valid @RequestBody TaskDto TaskDto) {
        return taskService.create(TaskDto);
    }

    @PatchMapping
    public TaskDto edit(@Valid @RequestBody TaskDto TaskDto) {
        return taskService.update(TaskDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        taskService.delete(id);
    }

    ;

    @GetMapping("/byDashboard/{dashboardId}")

    public List<TaskDto> get(@PathVariable UUID dashboardId) {
        return taskService.getByDashboardId(dashboardId);
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable("id") UUID id) {
        return taskService.getById(id);
    }

    @PostMapping("/ai")
    public TaskDto createTaskByAi(@RequestBody AiCreateTaskDto dto) {
        return taskService.generateTasksWithAi(dto);
    }

}
