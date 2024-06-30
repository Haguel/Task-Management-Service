package com.task_management_service.backend.test_utils;

import com.task_management_service.backend.entity.TaskEntity;
import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.enumeration.Role;
import com.task_management_service.backend.enumeration.TaskStatus;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

public class TestValues {
    public static String getHash() {
        return "$2y$10$R6sdrACYVHHKppthXWDdRuCtLZ5/UTETc51bUfFySEYQrmZ.QzuhO";
    }

    public static UserEntity getUser(int counter, String passwordHash) {
        return new UserEntity(
                "Mike" + counter,
                "MikeUsername" + counter,
                "mike" + counter + "@gmail.com",
                passwordHash,
                Role.USER
        );
    }

    public static UserEntity getUser(int counter) {
        return getUser(counter, "PasswordHash" + counter);
    }

    public static UserEntity getUser() {
        return getUser(1);
    }

    public static TaskEntity getTask(int counter, LocalDateTime localDateTime, TaskStatus status) {
        TaskEntity task = new TaskEntity();
        task.setTitle("Task" + counter);
        task.setDescription("Task" + counter + " desc");
        task.setUntilDate(localDateTime);
        task.setStatus(status);

        return task;
    }

    public static TaskEntity getTask(int counter, LocalDateTime localDateTime) {
        return getTask(counter, localDateTime, TaskStatus.TODO);
    }

    public static TaskEntity getTask(int counter) {
        return getTask(counter, LocalDateTime.now(), TaskStatus.TODO);
    }

    public static TaskEntity getTask() {
        return getTask(1, LocalDateTime.now(), TaskStatus.TODO);
    }

    public static String getSigningToken() {
        return "6d653c555a2726326d3b2b2178766c7377753f654e414b525775522331";
    }
    public static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(getSigningToken());

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String getJwtToken(Key signingKey, UserDetails userDetails, Date expiration) {
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String getJwtToken(Date expiration) {
        return getJwtToken(getSigningKey(), getUser(), expiration);
    }

    public static String getJwtToken() {
        return getJwtToken(getSigningKey(), getUser(), new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
    }
}
