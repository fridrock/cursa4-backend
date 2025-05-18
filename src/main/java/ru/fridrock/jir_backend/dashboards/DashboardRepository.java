package ru.fridrock.jir_backend.dashboards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DashboardRepository extends JpaRepository<DashboardEntity, UUID> {
  @Query("SELECT d FROM DashboardEntity d WHERE d.owner.id = :userId")
  List<DashboardEntity> findByUserId(@Param("userId") UUID userId);
}
