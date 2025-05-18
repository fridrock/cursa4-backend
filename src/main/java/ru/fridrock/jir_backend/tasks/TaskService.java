package ru.fridrock.jir_backend.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import ru.fridrock.jir_backend.dashboards.DashboardRepository;
import ru.fridrock.jir_backend.exception.AiGenerationException;
import ru.fridrock.jir_backend.exception.NotFoundException;
import ru.fridrock.jir_backend.tasks.dto.AiCreateTaskDto;
import ru.fridrock.jir_backend.tasks.dto.AiTaskDto;
import ru.fridrock.jir_backend.tasks.dto.TaskDto;
import ru.fridrock.jir_backend.tasks.enums.TaskPriority;
import ru.fridrock.jir_backend.tasks.enums.TaskSource;
import ru.fridrock.jir_backend.utils.IMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private static final String prompt = """
                Here is input for task: %s
                Current Date is %s.  
                Parse date offset from inputText
                Determine deadline using formula: current date + offset
                Determine title - name for task from inputText, don't provide information about date, or priority
                Determine description for task from inputText 
                Determine priority of task from inputText
                Priority can be strictly only one of three values: LOW, HIGH, CRITICAL
                create json with deadline, title, description, priority only for one task
                answer with only that json
        """;
    private final IMapper<TaskDto, TaskEntity> mapper;
    private final DashboardRepository dashboardRepository;
    private final OllamaChatModel ollamaChatModel;
    private final ObjectMapper objectMapper;

    public TaskDto create(TaskDto dto) {
        var dashboard = dashboardRepository.findById(dto.dashboardId())
            .orElseThrow(() -> dashboardNotFound(dto.dashboardId()));
        var task = TaskEntity.builder()
            .dashboard(dashboard)
            .title(dto.title())
            .description(dto.description())
            .issued(LocalDateTime.now())
            .deadline(dto.deadline())
            .hoursSpent(0)
            .priority(dto.priority())
            .source(ObjectUtils.isNotEmpty(dto.source()) ? dto.source() : TaskSource.PERSON)
            .build();
        dashboard.getTasks()
            .add(task);
        return mapper.mapToDto(taskRepository.save(task));
    }

    public TaskDto update(TaskDto dto) {
        var taskEntity = taskRepository.findById(dto.taskId())
            .orElseThrow(() -> taskNotFound(dto.taskId()));
        taskEntity = taskEntity.toBuilder()
            .description(dto.description())
            .title(dto.title())
            .deadline(dto.deadline())
            .priority(dto.priority())
            .hoursSpent(dto.hoursSpent())
            .build();
        return mapper.mapToDto(taskRepository.save(taskEntity));
    }

    public void delete(UUID taskId) {
        var taskEntity = taskRepository.findById(taskId)
            .orElseThrow(() -> taskNotFound(taskId));
        taskRepository.delete(taskEntity);
    }

    public TaskDto getById(UUID taskId) {
        var task = taskRepository.findById(taskId)
            .orElseThrow(() -> taskNotFound(taskId));
        return mapper.mapToDto(task);
    }

    public List<TaskDto> getByDashboardId(UUID dashboardId) {
        List<TaskEntity> tasks = taskRepository.findByDashboardId(dashboardId);
        return tasks.stream()
            .map(mapper::mapToDto)
            .collect(Collectors.toList());
    }

    private NotFoundException dashboardNotFound(UUID dashboardId) {
        return new NotFoundException("dashboard with id: " +
            dashboardId +
            " wasn't found");
    }

    private NotFoundException taskNotFound(UUID taskId) {
        return new NotFoundException("task with id: " +
            taskId +
            " wasn't found");
    }

    public TaskDto generateTasksWithAi(AiCreateTaskDto dto) {
        String promptText = String.format(prompt, dto.message(), LocalDateTime.now());
        ChatResponse response = ollamaChatModel.call(new Prompt(promptText));

        String output = response.getResult()
            .getOutput()
            .getText()
            .replaceAll("```json|```", "")
            .trim();
        ;
        log.info(output);
        try {
            AiTaskDto aiTask = objectMapper.readValue(output, AiTaskDto.class);
            log.info("received ai task: {}", aiTask);
            TaskDto taskToCreate = taskDtoFromAiTask(aiTask, dto.dashboardId());
            log.info("mapped to task: {}", taskToCreate);
            TaskDto created = create(taskToCreate);
            log.info("created task: {}", created);
            return created;
        } catch (JsonProcessingException ex) {
            throw new AiGenerationException("Failed to parse AI response");
        }
    }

    private TaskDto taskDtoFromAiTask(AiTaskDto aiTask, UUID dashboardId) {
        return TaskDto.builder()
            .dashboardId(dashboardId)
            .source(TaskSource.AI)
            .deadline(parseDeadline(aiTask.deadline()))
            .description(aiTask.description())
            .title(aiTask.title())
            .priority(TaskPriority.valueOf(aiTask.priority()))
            .build();
    }

    private LocalDateTime parseDeadline(String deadline) {
        return LocalDateTime.parse(deadline);
    }
}
