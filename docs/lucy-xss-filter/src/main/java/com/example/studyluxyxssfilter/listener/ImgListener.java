package com.example.studyluxyxssfilter.listener;

import com.nhncorp.lucy.security.xss.event.ElementListener;
import com.nhncorp.lucy.security.xss.markup.Element;

public class ImgListener implements ElementListener {

    public void handleElement(Element element) {
        String id = element.getAttributeValue("id");
        String srcValue = "'http://local.cafe.naver.com/MoviePlayer.nhn?dir=" + id + "?key=";

        element.setName("iframe");

        boolean removed = element.removeAllAttributes();
        if (removed) {
            element.putAttribute("frameborder", "'no'");
            element.putAttribute("width", "342");
            element.putAttribute("height", "296");
            element.putAttribute("scrolling", "no");
            element.putAttribute("name", "'mplayer'");
            element.putAttribute("src", srcValue);
        }
        element.setClose(true);
    }
}