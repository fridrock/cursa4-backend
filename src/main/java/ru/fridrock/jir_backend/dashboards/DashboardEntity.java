package ru.fridrock.jir_backend.dashboards;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fridrock.jir_backend.tasks.TaskEntity;
import ru.fridrock.jir_backend.user.UserEntity;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "dashboards")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DashboardEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID dashboardId;
  @ManyToOne
  private UserEntity owner;
  private String name;
    @OneToMany(mappedBy = "dashboard",
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private Set<TaskEntity> tasks;
}
