package com.task_management_service.backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
    private static final Pattern ISO_8601_PATTERN = Pattern.compile(RegexpBase.ISO8601_REGEXP);
    private static final DateTimeFormatter ISO_8601_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    public static UUID convertStringToUUID(String strUUID) {
        boolean isUUID = RegexpChecker.isMatches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}", strUUID);

        if(isUUID) {
            return UUID.fromString(strUUID);
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can not convert Id from request json");
    }

    public static LocalDateTime convertToLocalDateTime(String iso8601) {
        Matcher matcher = ISO_8601_PATTERN.matcher(iso8601);
        if (matcher.matches()) {
            try {
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(iso8601, ISO_8601_FORMATTER);

                return offsetDateTime.toLocalDateTime().withSecond(0).withNano(0);
            } catch (DateTimeParseException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can not convert ISO8601 to LocalDateTime");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can not convert ISO8601 to LocalDateTime");
        }
    }
}
