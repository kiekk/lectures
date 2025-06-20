package com.example.studyluxyxssfilter.util;

public class StringUtil {
    public static String leftPad(String str, String padChar, int length) {
        if (str == null) {
            str = "";
        }
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < length) {
            strBuilder.insert(0, padChar);
        }
        return strBuilder.toString();
    }

    public static String rightPad(String str, String padChar, int length) {
        if (str == null) {
            str = "";
        }
        StringBuilder strBuilder = new StringBuilder(str);
        while (strBuilder.length() < length) {
            strBuilder.append(padChar);
        }
        return strBuilder.toString();
    }

    public static boolean isStringEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
