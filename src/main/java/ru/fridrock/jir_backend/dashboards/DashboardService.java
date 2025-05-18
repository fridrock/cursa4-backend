package ru.fridrock.jir_backend.dashboards;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.fridrock.jir_backend.exception.NotFoundException;
import ru.fridrock.jir_backend.user.UserEntity;
import ru.fridrock.jir_backend.user.UserRepository;
import ru.fridrock.jir_backend.utils.IMapper;
import ru.fridrock.jir_backend.utils.security.SecurityContextHolderUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {
    private final DashboardRepository dashboardRepository;
    private final UserRepository userRepository;
    private final IMapper<DashboardDto, DashboardEntity> mapper;

    public DashboardDto create(DashboardDto dto) {
        var userId = SecurityContextHolderUtils.getUser()
            .getUserId();
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("user with id: " + userId + " wasn't found"));

        DashboardEntity dashboardEntity = DashboardEntity.builder()
            .name(dto.name())
            .owner(user)
            .build();

        userRepository.save(user);
        return mapper.mapToDto(dashboardRepository.save(dashboardEntity));
    }

    public DashboardDto update(DashboardDto dto) {
        var dashboard = dashboardRepository.findById(dto.dashboardId())
            .orElseThrow(() -> dashboardNotFoundException(dto.dashboardId()));
        dashboard.setName(dto.name());
        return mapper.mapToDto(dashboardRepository.save(dashboard));
    }

    public void delete(UUID dashboardId) {
        var dashboard = dashboardRepository.findById(dashboardId)
            .orElseThrow(() -> dashboardNotFoundException(dashboardId));
        dashboardRepository.delete(dashboard);
    }

    public List<DashboardDto> getDashboards() {
        var userId = SecurityContextHolderUtils.getUser()
            .getUserId();
        List<DashboardEntity> dashboards = dashboardRepository.findByUserId(userId);
        return dashboards.stream()
            .map(mapper::mapToDto)
            .collect(Collectors.toList());
    }

    public DashboardDto getById(UUID id) {
        return mapper.mapToDto(dashboardRepository.findById(id)
            .orElseThrow(() -> dashboardNotFoundException(id)));
    }

    private NotFoundException dashboardNotFoundException(UUID dashboardId) {
        return new NotFoundException("dashboard with id: " + dashboardId + " wasn't found");
    }
}
