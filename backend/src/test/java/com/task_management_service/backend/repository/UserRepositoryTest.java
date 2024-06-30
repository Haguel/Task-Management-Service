package com.task_management_service.backend.repository;

import com.task_management_service.backend.config.TestSecurityConfig;
import com.task_management_service.backend.test_utils.TestValues;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import com.task_management_service.backend.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(TestSecurityConfig.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindUserEntityByUsernameOrEmail() {
        UserEntity user = TestValues.getUser();

        userRepository.save(user);

        UserEntity returned = userRepository
                .findUserEntityByUsernameOrEmail(user.getUsername(), user.getEmail()).orElse(null);

        assertThat(returned).isNotNull();
        assertThat(returned.getId()).isEqualTo(user.getId());
    }

    @Test
    void findUserEntityByEmail() {
        UserEntity user = TestValues.getUser();

        userRepository.save(user);

        UserEntity returned = userRepository
                .findUserEntityByEmail(user.getEmail()).orElse(null);

        assertThat(returned).isNotNull();
        assertThat(returned.getId()).isEqualTo(user.getId());
    }
}