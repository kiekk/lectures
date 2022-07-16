package handlebar.study.helper;

import com.github.jknack.handlebars.Handlebars;
import org.springframework.stereotype.Component;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@Component
@HandlebarsHelper
public class CustomHelper {
    public String compare(int param1, int param2) {
        return param1 + ", " + param2 + " 중 더 큰 수는 " + Math.max(param1, param2) + " 입니다.";
    }

    public boolean compareBoolean(int param1, int param2) {
        return param1 > param2;
    }

    public String html() {
        return "<div><a href=\"naver.com\">naver</a></div>";
    }

    public Handlebars.SafeString safeStringHtml() {
        return new Handlebars.SafeString("<div><a href=\"naver.com\">naver</a></div>");
    }
}
