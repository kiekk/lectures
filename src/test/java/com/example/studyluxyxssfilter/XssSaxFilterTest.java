package com.example.studyluxyxssfilter;

import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssSaxFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class XssSaxFilterTest extends XssFilterTestCase {

    private static final String NORMAL_HTML_FILE = "xss-normal1.html";
    private static final String INVALID_HTML_FILE3 = "xss-invalid3.html";

    /**
     * 표준 HTML 페이지를 통과 시키는지 검사한다.(필터링 전후가 동일하면 정상)
     * @throws Exception
     */
    @Test
    public void testStandardHtmlFiltering() throws Exception {
        LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-superset-sax.xml");
        String valid = readString(NORMAL_HTML_FILE);
        String clean = filter.doFilter(valid);
        System.out.println("valid: " + valid);
        System.out.println("clean: " + clean);
        assertEquals(valid, clean);
    }

    /**
     * 비표준 HTML 페이지를 통과 시키는지 검사한다.(필터링 전후가 동일하면 정상)
     * @throws Exception
     */
    @Test
    public void testNonStandardHtmlFiltering() throws Exception {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-superset-sax.xml");
        String dirty = readString(INVALID_HTML_FILE3);
        String clean = filter.doFilter(dirty);
        System.out.println("dirty: " + dirty);
        System.out.println("clean: " + clean);
        assertEquals(dirty, clean);
    }
}
