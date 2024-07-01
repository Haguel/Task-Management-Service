package com.task_management_service.backend.service;

import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.helper.JwtServiceHelper;
import com.task_management_service.backend.test_utils.TestValues;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    @Mock
    private JwtServiceHelper jwtServiceHelper;
    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        reset(jwtServiceHelper);
    }

    @Test
    void itShouldGenerateToken() {
        UserEntity user = TestValues.getUser();
        String pattern = "^[A-Za-z0-9\\-_=]+\\.[A-Za-z0-9\\-_=]+(\\.[A-Za-z0-9\\-_.+/=]+)?$";

        when(jwtServiceHelper.getSigningKey()).thenReturn(TestValues.getSigningKey());

        String token = jwtService.generateToken(user);

        assertThat(token).isNotNull();
        assertTrue(Pattern.matches(pattern, token));
    }
}