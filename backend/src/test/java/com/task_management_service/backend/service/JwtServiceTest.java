package com.task_management_service.backend.service;

import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.helper.JwtServiceHelper;
import com.task_management_service.backend.test_utils.TestValues;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({Jwts.class, DefaultJwtBuilder.class})
public class JwtServiceTest {
    @Mock
    private JwtServiceHelper jwtServiceHelper;
    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        reset(jwtServiceHelper);
        PowerMockito.mockStatic(Jwts.class);
        DefaultJwtBuilder defaultJwtBuilder = PowerMockito.mock(DefaultJwtBuilder.class);
        when(Jwts.builder()).thenReturn(defaultJwtBuilder);
        when(defaultJwtBuilder.setClaims(any(HashMap.class))).thenReturn(defaultJwtBuilder);
        when(defaultJwtBuilder.setSubject(anyString())).thenReturn(defaultJwtBuilder);
        when(defaultJwtBuilder.setIssuedAt(any(Date.class))).thenReturn(defaultJwtBuilder);
        when(defaultJwtBuilder.setExpiration(any(Date.class))).thenReturn(defaultJwtBuilder);
        when(defaultJwtBuilder.signWith(any(), any(SignatureAlgorithm.class))).thenReturn(defaultJwtBuilder);
        when(defaultJwtBuilder.compact()).thenReturn("mocked-token");

    }

    @Test
    void itShouldPutUsername() {
        UserEntity user = TestValues.getUser();


        when(jwtServiceHelper.getSigningKey()).thenReturn(TestValues.getSigningKey());

        jwtService.generateToken(user);

        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        PowerMockito.verifyStatic(Jwts.class);
        Jwts.builder().setSubject(usernameArgumentCaptor.capture());

        verify(Jwts.builder().setSubject(usernameArgumentCaptor.capture()));

        assertThat(usernameArgumentCaptor.getValue()).isNotNull();
        assertThat(usernameArgumentCaptor.getValue()).isEqualTo(user.getUsername());
    }

    @Test
    void itShouldGenerateToken() {
        UserEntity user = TestValues.getUser();
        String pattern = "^[A-Za-z0-9\\-_=]+\\.[A-Za-z0-9\\-_=]+(\\.[A-Za-z0-9\\-_.+/=]+)?$";
        String token = jwtService.generateToken(user);

        assertThat(token).isNotNull();
        assertTrue(Pattern.matches(pattern, token));
    }

    @Test
    void shouldConsiderTokenValid() {
        UserEntity user = TestValues.getUser();
        String token = jwtService.generateToken(user);

        boolean isTokenValid = jwtService.isTokenValid(token, user);

        assertTrue(isTokenValid);
    }

    @Test
    void shouldNotConsiderTokenValid() {
        UserDetails user = TestValues.getUser();
        String jwtToken = TestValues.getJwtToken();

        assertThrows(Exception.class, () -> jwtService.isTokenValid(jwtToken, user));
    }
}