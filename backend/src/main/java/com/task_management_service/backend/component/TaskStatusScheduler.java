package com.task_management_service.backend.component;

import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.enumeration.TaskStatus;
import com.task_management_service.backend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskStatusScheduler {
    private final TaskRepository taskRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void updateExpiredTasks() {
        LocalDateTime now = LocalDateTime.now();
        List<TaskEntity> expiredTasks = taskRepository.findByUntilDateBeforeAndStatusNot(now, TaskStatus.EXPIRED);

        for (TaskEntity task : expiredTasks) {
            task.setStatus(TaskStatus.EXPIRED);
            taskRepository.save(task);

            messagingTemplate.convertAndSend("/tasks/" + task.getUser().getId(), task.getUser().getTasks());
        }
    }
}
