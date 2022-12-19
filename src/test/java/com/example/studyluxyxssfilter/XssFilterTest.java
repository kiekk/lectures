package com.example.studyluxyxssfilter;

import com.nhncorp.lucy.security.xss.XssFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class XssFilterTest {

    @Test
    public void pairQuoteCheckOtherCase() {
        XssFilter filter = XssFilter.getInstance("lucy-xss-superset.xml");
        String dirty = "<img src=\"<img src=1\\ onerror=alert(1234)>\" onerror=\"alert('XSS')\">";
        String expected = "<img src=\"\"><!-- Not Allowed Attribute Filtered ( onerror=alert(1234)) --><img src=1\\>\" onerror=\"alert('XSS')\"&gt;";
        String actual = filter.doFilter(dirty);

        System.out.println("dirty : " + dirty);
        System.out.println("actual : " + actual);
        assertEquals(expected, actual);

        dirty = "<img src='<img src=1\\ onerror=alert(1234)>\" onerror=\"alert('XSS')\">";
        expected = "<img src=''><!-- Not Allowed Attribute Filtered ( onerror=alert(1234)) --><img src=1\\>\" onerror=\"alert('XSS')\"&gt;";
        actual = filter.doFilter(dirty);

        System.out.println("dirty : " + dirty);
        System.out.println("actual : " + actual);
        assertEquals(expected, actual);
    }

}
