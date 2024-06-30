package com.task_management_service.backend.helper;

import com.task_management_service.backend.test_utils.TestValues;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtServiceHelperTest {
    private JwtServiceHelper jwtServiceHelper;

    @BeforeAll
    void setUp() {
        jwtServiceHelper = new JwtServiceHelper();
        ReflectionTestUtils.setField(jwtServiceHelper, "jwtSigningToken", TestValues.getSigningToken());
    }

    @Test
    void shouldNotConsiderTokenExpired() {
        String nonExpiredJwtToken1 = TestValues.getJwtToken(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        String nonExpiredJwtToken2 = TestValues.getJwtToken(new Date(System.currentTimeMillis() + 1000 * 60));
        String nonExpiredJwtToken3 = TestValues.getJwtToken(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 65));

        assertFalse(jwtServiceHelper.isTokenExpired(nonExpiredJwtToken1));
        assertFalse(jwtServiceHelper.isTokenExpired(nonExpiredJwtToken2));
        assertFalse(jwtServiceHelper.isTokenExpired(nonExpiredJwtToken3));
    }

    @Test
    void shouldConsiderTokenExpired() {
        String expiredJwtToken1 = TestValues.getJwtToken(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        String expiredJwtToken2 = TestValues.getJwtToken(new Date(System.currentTimeMillis() - 1000 * 60));
        String expiredJwtToken3 = TestValues.getJwtToken(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 65));

        assertTrue(jwtServiceHelper.isTokenExpired(expiredJwtToken1));
        assertTrue(jwtServiceHelper.isTokenExpired(expiredJwtToken2));
        assertTrue(jwtServiceHelper.isTokenExpired(expiredJwtToken3));
    }
}