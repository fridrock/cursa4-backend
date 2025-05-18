package ru.fridrock.jir_backend.dashboards;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.fridrock.jir_backend.utils.IMapper;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DashboardMapper extends IMapper<DashboardDto, DashboardEntity> {
    @Override
    DashboardDto mapToDto(DashboardEntity entity);

    @Override
    DashboardEntity mapToEntity(DashboardDto dto);

}
