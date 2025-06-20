package com.example.studyluxyxssfilter.listener;

import com.nhncorp.lucy.security.xss.event.ElementListener;
import com.nhncorp.lucy.security.xss.markup.Element;

public class ATagListener implements ElementListener {
    public void handleElement(Element element) {
        String styleValue = element.getAttributeValue("style");

        if (styleValue.contains("fixed")) {
            element.putAttribute("href", "http");
            element.putAttribute("style", "color");
        }
    }
}