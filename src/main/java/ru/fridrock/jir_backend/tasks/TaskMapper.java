package ru.fridrock.jir_backend.tasks;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.fridrock.jir_backend.tasks.dto.TaskDto;
import ru.fridrock.jir_backend.utils.IMapper;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper extends IMapper<TaskDto, TaskEntity> {
    @Override
    @Mapping(target = "dashboardId",
        source = "dashboard.dashboardId")
    TaskDto mapToDto(TaskEntity entity);

    @Override
    TaskEntity mapToEntity(TaskDto dto);
}