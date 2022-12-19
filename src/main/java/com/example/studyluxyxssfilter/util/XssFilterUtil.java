package com.example.studyluxyxssfilter.util;

import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssFilter;

public class XssFilterUtil {
    public static String xssEscape(String str) {
        if (StringUtil.isStringEmpty(str)) {
            return "";
        }
        LucyXssFilter filter = XssFilter.getInstance("xss/lucy-xss.xml", true);
        return filter.doFilter(str);
    }
}
