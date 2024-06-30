package com.task_management_service.backend.repository;

import com.task_management_service.backend.config.TestSecurityConfig;
import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.enumeration.TaskStatus;
import com.task_management_service.backend.test_utils.TestValues;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestSecurityConfig.class)
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();
    }

    @Test
    void shouldFindByUntilDateBeforeAndStatusNot() {
        UserEntity user = TestValues.getUser();
        TaskEntity task1 = TestValues.getTask(1, LocalDateTime.now().plus(1, ChronoUnit.DAYS), TaskStatus.DOING);
        task1.setUser(user);
        TaskEntity task2 = TestValues.getTask(2, LocalDateTime.now().minus(1, ChronoUnit.DAYS), TaskStatus.FINISHED);
        task2.setUser(user);
        TaskEntity task3 = TestValues.getTask(3, LocalDateTime.now().plus(2, ChronoUnit.DAYS), TaskStatus.TODO);
        task3.setUser(user);

        taskRepository.saveAll(Arrays.asList(task1, task2, task3));

        List<TaskEntity> expiredTasksWithoutExpiredStatus = taskRepository.findByUntilDateBeforeAndStatusNot(
                LocalDateTime.now(), TaskStatus.EXPIRED);
        assertThat(expiredTasksWithoutExpiredStatus).isNotNull();
        assertThat(expiredTasksWithoutExpiredStatus.size()).isEqualTo(1);

        List<TaskEntity> expiredTasksWithoutFinishedStatus = taskRepository.findByUntilDateBeforeAndStatusNot(
                LocalDateTime.now(), TaskStatus.FINISHED);
        assertThat(expiredTasksWithoutFinishedStatus).isNotNull();
        assertThat(expiredTasksWithoutFinishedStatus.size()).isEqualTo(0);

        List<TaskEntity> expiredTasksWithoutTodoStatus = taskRepository.findByUntilDateBeforeAndStatusNot(
                LocalDateTime.now().minus(5, ChronoUnit.DAYS), TaskStatus.TODO);
        assertThat(expiredTasksWithoutTodoStatus).isNotNull();
        assertThat(expiredTasksWithoutTodoStatus.size()).isEqualTo(0);
    }
}