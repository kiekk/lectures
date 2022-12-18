package com.example.studyluxyxssfilter;

import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssPreventer;
import com.nhncorp.lucy.security.xss.XssSaxFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class XssPreventerTest {

    /*
    XssPreventer 객체의 escape 메소드를 사용하여 XSS 방어
    <,> 또는 "", '' 만 변환해준다.
     */
    @Test
    public void xssPreventerTest() {
        String dirty = "<img src=\"javascript:alert(\"test\");\"></img>";
        String clean = XssPreventer.escape(dirty);

        System.out.println("clean : " + clean);
        assertEquals("&lt;img src=&quot;javascript:alert(&quot;test&quot;);&quot;&gt;&lt;/img&gt;", clean);
    }

}
