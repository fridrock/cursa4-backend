package ru.fridrock.jir_backend.dashboards;

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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dashboards")
@RequiredArgsConstructor
@SecurityRequirement(name="Authorization")
public class DashboardsController {

    private final DashboardService dashboardService;

  @PostMapping
  public DashboardDto create(@RequestBody @Valid DashboardDto dashboardDto) {
      System.out.println(dashboardDto.name());
      return dashboardService.create(dashboardDto);
  }

  @PatchMapping
  public DashboardDto edit(@RequestBody @Valid DashboardDto dashboardDto) {
      return dashboardService.update(dashboardDto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable UUID id){
      dashboardService.delete(id);
  };
  @GetMapping
  public List<DashboardDto> get() {
      return dashboardService.getDashboards();
  }

  @GetMapping("/{id}")
  public DashboardDto getById(@PathVariable("id") UUID id) {
      return dashboardService.getById(id);
  }
}
