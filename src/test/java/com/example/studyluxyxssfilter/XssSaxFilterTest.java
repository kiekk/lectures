package com.example.studyluxyxssfilter;

import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssSaxFilter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class XssSaxFilterTest extends XssFilterTestCase {

    private static final String DIRTY_CODES_FILE = "xss-dirtycodes.txt";
    private static final String NORMAL_HTML_FILE = "xss-normal1.html";
    private static final String INVALID_HTML_FILE1 = "xss-invalid1.html";
    private static final String INVALID_HTML_FILE2 = "xss-invalid2.html";
    private static final String INVALID_HTML_FILE3 = "xss-invalid3.html";

    /**
     * 표준 HTML 페이지를 통과 시키는지 검사한다.(필터링 전후가 동일하면 정상)
     *
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
     *
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

    /**
     * JavaScript와 같은 공격적인 코드를 필터링 하는지 검사한다.(필터링 전후가 틀려야 정상)
     *
     * @throws Exception
     */
    @Test
    public void testDirtyCodeFiltering() throws Exception {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-superset-sax.xml");
        String dirty = readString(DIRTY_CODES_FILE);
        String clean = filter.doFilter(dirty);
        System.out.println("dirty: " + dirty);
        System.out.println("clean: " + clean);
        assertNotEquals(dirty, clean);
    }

    /**
     * 허용되지 않은 element, attribute 를 필터링 하는지 검사한다. (필터링 전후가 틀려야 정상)
     *
     * @throws Exception
     */
    @Test
    public void testCrackCodeFiltering() throws Exception {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-superset-sax.xml");
        String dirty = readString(INVALID_HTML_FILE1);
        String clean = filter.doFilter(dirty);
        String expected = "<html><head><title>제품 정보</title></head><!-- Not Allowed Tag Filtered -->&lt;body&gt;<form name=\"myform\" action=\"\" method=\"post\"><h2 align=\"center\">제품 정보</h2><input type=\"submit\" value=\"등록하기\"> &nbsp; <input type=\"reset\">value=\"취소\"&gt;</p>&lt;/body&gt;</html>";
        assertEquals(expected, clean);

        dirty = readString(INVALID_HTML_FILE2);
        clean = filter.doFilter(dirty);
        expected = "<a href=\"naver.com\" name=\"rich\">참고</a>하세요.";
        assertEquals(expected, clean);
    }

    /**
     * White Url을 포함하지 않은 Embed 태그에 대한 보안 필터링 하는지 검사한다.
     *
     * @throws Exception
     */
    @Test
    public void testEmbedListener() throws Exception {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-sax-cafe-child.xml");

        String dirty = "<EMBED src=\"http://medlabum.com/cafe/0225/harisu.wmv\" width=\"425\" height=\"344\">";
        String expected = "<EMBED src=\"http://medlabum.com/cafe/0225/harisu.wmv\" width=\"425\" height=\"344\" type=\"video/x-ms-wmv\" invokeURLs=\"false\" autostart=\"false\" allowScriptAccess=\"never\" allowNetworking=\"internal\">";
        String clean = filter.doFilter(dirty);

        System.out.println("expected : " + expected);
        System.out.println("clean    : " + clean);
        assertEquals(expected, clean);
    }

    /**
     * White Url을 포함한 Embed 태그에 대한 보안 필터링 하는지 검사한다.
     *
     * @throws Exception
     */
    @Test
    public void testEmbedListenerWithWhiteUrl() throws Exception {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-sax-cafe-child.xml");

        String dirty = "<EMBED src=\"http://play.tagstory.com/player/harisu.wmv\" width=\"425\" height=\"344\">";
        String expected = "<EMBED src=\"http://play.tagstory.com/player/harisu.wmv\" width=\"425\" height=\"344\" invokeURLs=\"false\" autostart=\"false\" allowScriptAccess=\"never\" allowNetworking=\"all\">";
        String clean = filter.doFilter(dirty);

        System.out.println("expected : " + expected);
        System.out.println("clean    : " + clean);
        assertEquals(expected, clean);
    }

    /**
     * disable 속성 테스트
     */
    @Test
    public void testElementDisabledSimple() {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml");

        String dirty = "<html><head></head><body><p>Hello</p></body>";
        String expected = "<!-- Not Allowed Tag Filtered -->&lt;html&gt;<head></head><!-- Not Allowed Tag Filtered -->&lt;body&gt;<p>Hello</p>&lt;/body&gt;";
        String clean = filter.doFilter(dirty);

        System.out.println("expected : " + expected);
        System.out.println("clean    : " + clean);
        assertEquals(expected, clean);
    }

    /**
     * removeTag 속성 테스트
     */
    @Test
    public void testElementRemoveSimple() {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-sax-blog-removetag.xml");

        String dirty = "<html><head></head><body><p>Hello</p></body>";
        String expected = "<!-- Removed Tag Filtered (html) --><!-- Removed Tag Filtered (head) --><!-- Removed Tag Filtered (body) --><p>Hello</p>";
        String clean = filter.doFilter(dirty);

        System.out.println("expected : " + expected);
        System.out.println("clean    : " + clean);
        assertEquals(expected, clean);
    }


}
