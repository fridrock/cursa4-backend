package ru.fridrock.jir_backend.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository
        extends JpaRepository<TaskEntity, UUID> {

    @Query("SELECT t FROM TaskEntity t WHERE t.dashboard.dashboardId = :dashboardId")
    List<TaskEntity> findByDashboardId(@Param("dashboardId") UUID dashboardId);
}
