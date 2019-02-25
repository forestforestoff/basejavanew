package ru.javaops.webapp.util;

public class ServletUtil {
    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }
}