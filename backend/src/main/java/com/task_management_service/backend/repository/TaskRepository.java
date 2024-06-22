package com.task_management_service.backend.repository;

import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.enumeration.TaskStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, UUID> {
    List<TaskEntity> findByUntilDateBeforeAndStatusNot(LocalDateTime untilDate, TaskStatus status);
}
