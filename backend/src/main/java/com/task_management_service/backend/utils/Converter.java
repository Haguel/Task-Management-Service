package com.task_management_service.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class Converter {
    public static UUID convertStringToUUID(String strUUID) {
        boolean isUUID = RegexChecker.isMatches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}", strUUID);

        if(isUUID) {
            return UUID.fromString(strUUID);
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can not convert Id from request json");
    }
}
