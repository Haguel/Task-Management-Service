package com.task_management_service.backend.test_utils;

import com.task_management_service.backend.entity.UserEntity;
import com.task_management_service.backend.enumeration.Role;

public class TestValues {
    public static String getHash() {
        return "$2y$10$R6sdrACYVHHKppthXWDdRuCtLZ5/UTETc51bUfFySEYQrmZ.QzuhO";
    }

    public static UserEntity getUser(String passwordHash) {
        return new UserEntity(
                "Mike",
                "Mike1902",
                "mike1999@gmail.com",
                passwordHash,
                Role.USER
        );
    }

    public static UserEntity getUser() {
        return new UserEntity(
                "Mike",
                "Mike1902",
                "mike1999@gmail.com",
                "PasswordHash1234",
                Role.USER
        );
    }

    public static String getToken() {
        return "6d653c555a2726326d3b2b2178766c7377753f654e414b525775522331";
    }
}
