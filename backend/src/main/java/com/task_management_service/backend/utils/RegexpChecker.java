package com.task_management_service.backend.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpChecker {
    public static boolean isMatches(String regexp, String stringToCheck) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(stringToCheck);

        return matcher.matches();
    }
}
