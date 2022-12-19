package com.example.studyluxyxssfilter.util;

import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssSaxFilter;

public class XssSaxFilterUtil {
    public static String xssEscape(String str) {
        if (StringUtil.isStringEmpty(str)) {
            return "";
        }
        LucyXssFilter filter = XssSaxFilter.getInstance("xss/lucy-xss-sax.xml", true);
        return filter.doFilter(str);
    }
}
