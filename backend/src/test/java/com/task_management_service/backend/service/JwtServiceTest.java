package com.task_management_service.backend.service;

import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.test_utils.TestValues;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Date;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;
    private String jwtSigningKey = TestValues.getToken();

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "jwtSigningKey", jwtSigningKey);
    }

    @Test
    void itShouldExtractUsername() {
        UserEntity user = TestValues.getUser();
        String token = jwtService.generateToken(user);
        String username = jwtService.extractUsername(token);

        assertThat(username).isNotNull();
        assertThat(username).isEqualTo(user.getUsername());
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
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        Key signingKey =  Keys.hmacShaKeyFor(keyBytes);
        UserEntity user = TestValues.getUser();
        String token = Jwts
                .builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() - 1000000))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

        assertThrows(Exception.class, () -> jwtService.isTokenValid(token, user));
    }
}