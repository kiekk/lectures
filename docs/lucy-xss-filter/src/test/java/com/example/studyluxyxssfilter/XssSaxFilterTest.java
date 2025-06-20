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

    /**
     * removeTag 속성 테스트 - removeTag 속성 및 ContentsRemoveListener 기능을 테스트한다.
     * SAX 스펙상 자식 콘텐츠를 삭제하는 ContentsRemoveListener 기능이 동작하지 않아야 한다.
     */
    @Test
    public void testElementRemoveBlogRequest() {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-sax-blog-removetag.xml");
        String dirty = "<html><head><style>P {margin-top:2px;margin-bottom:2px;}</style></head><body><div style=\"font-size:10pt; font-family:gulim;\"><div style=\"padding:0 0 0 10pt\"><p style=\"\">한글테스트에용~~~&nbsp;</p><p style=\"\">한글테스트에용~~~한글테스트에용~~~한글테스트에용~~~</p><p style=\"\">한글테스트에용~~~한글테스트에용~~~</p><p style=\"font-size:pt; font-family:,AppleGothic,sans-serif\"><img class=\"NHN_MAIL_IMAGE\" src=\"http://postfiles2.naver.net/20111116_241/youreme_dev_1321429196418_lRlJSu_jpg/h_cafe_mail.jpg?type=w3\"><br></p><p style=\"font-size:10pt;FONT-FAMILY: Gulim,AppleGothic,sans-serif;padding:0 0 0 0pt\"><span>-----Original Message-----</span><br><b>From:</b> \"박태민\"&lt;youreme_dev@naver.com&gt; <br><b>To:</b> youreme_dev@naver.com<br><b>Cc:</b> <br><b>Sent:</b> 11-11-11(금) 10:24:55<br><b>Subject:</b> test.txt<br /></p></div></div></body></html>";
        String expected = "<!-- Removed Tag Filtered (html) --><!-- Removed Tag Filtered (head) --><!-- Not Allowed Tag Filtered -->&lt;style&gt;P {margin-top:2px;margin-bottom:2px;}&lt;/style&gt;<!-- Removed Tag Filtered (body) --><div style=\"font-size:10pt; font-family:gulim;\"><div style=\"padding:0 0 0 10pt\"><p style=\"\">한글테스트에용~~~&nbsp;</p><p style=\"\">한글테스트에용~~~한글테스트에용~~~한글테스트에용~~~</p><p style=\"\">한글테스트에용~~~한글테스트에용~~~</p><p style=\"font-size:pt; font-family:,AppleGothic,sans-serif\"><img class=\"NHN_MAIL_IMAGE\" src=\"http://postfiles2.naver.net/20111116_241/youreme_dev_1321429196418_lRlJSu_jpg/h_cafe_mail.jpg?type=w3\"><br></p><p style=\"font-size:10pt;FONT-FAMILY: Gulim,AppleGothic,sans-serif;padding:0 0 0 0pt\"><span>-----Original Message-----</span><br><b>From:</b> \"박태민\"&lt;youreme_dev@naver.com&gt; <br><b>To:</b> youreme_dev@naver.com<br><b>Cc:</b> <br><b>Sent:</b> 11-11-11(금) 10:24:55<br><b>Subject:</b> test.txt<br /></p></div></div>";
        String clean = filter.doFilter(dirty);

        System.out.println("dirty    : " + dirty);
        System.out.println("expected : " + expected);
        System.out.println("clean    : " + clean);
        assertEquals(expected, clean);
    }

    /**
     * src에 script 패턴이 존재 시 무조건 필터링 되는 문제 테스트
     */
    @Test
    public void notAllowedPatternSrcAttribute() {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-superset-sax.xml");

        String dirty = "<img src='http://sstorym.cafe24.com/deScription/lereve/lelogo.gif' width='700'>";
        String expected = "<img src='http://sstorym.cafe24.com/deScription/lereve/lelogo.gif' width='700'>";
        String clean = filter.doFilter(dirty);
        assertEquals(expected, clean);

        dirty = "<img src='scription/lereve/lelogo.gif' width='700'>";
        expected = "<img src='scription/lereve/lelogo.gif' width='700'>";
        clean = filter.doFilter(dirty);
        assertEquals(expected, clean);

        dirty = "<img src='script:/lereve/lelogo.gif' width='700'>";
        expected = "<!-- Not Allowed Attribute Filtered ( src='script:/lereve/lelogo.gif') --><img width='700'>";
        clean = filter.doFilter(dirty);
        assertEquals(expected, clean);
    }

    /**
     * 모든 태그에서 class 속성을 허용하지 않고, table 태그에서만 class 속성을 허용
     */
    @Test
    public void disableClassAttrExceptTable() {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-sax-simple.xml");
        // exceptionTagList 에 포함 된 element 는 attribute 의 disable 속성에 영향을 안 받도록 예외처리가 잘 되는지 확인.
        String dirty = "<table class='test'></table>";
        String expected = "<table class='test'></table>";
        String clean = filter.doFilter(dirty);
        assertEquals(expected, clean);

        // exceptionTagList 에 없는 태그들은 attribute 의 disable 속성이 true 되어 있으면 필터링 되는지 확인.
        dirty = "<div class='test'></div>";
        expected = "<!-- Not Allowed Attribute Filtered ( class='test') --><div></div>";
        clean = filter.doFilter(dirty);
        assertEquals(expected, clean);

        // exceptionTagList로 예외처리가 되어있고, element 의 속성요소로 설정이 안되어 있어도 자식 속성을 체크하지 않는 SAX 스펙상 통과
        dirty = "<span class='test'></span>";
        expected = "<span class='test'></span>";
        clean = filter.doFilter(dirty);
        assertEquals(expected, clean);

        // exceptionTagList로 예외처리가 되었어도, value 가 문제 있을 경우 disable 되는지 확인
        dirty = "<table class='script'></table>";
        expected = "<!-- Not Allowed Attribute Filtered ( class='script') --><table></table>";
        clean = filter.doFilter(dirty);
        assertEquals(expected, clean);

    }

    /**
     * Href 속성에서 javascript 패턴 존재하는지 테스트
     */
    @Test
    public void hrefAttackTest() {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-superset-sax.xml");
        String dirty = "<a HREF=\"javascript:alert('XSS');\">";
        String expected = "<!-- Not Allowed Attribute Filtered ( HREF=\"javascript:alert('XSS');\") --><a>";
        String clean = filter.doFilter(dirty);
        assertEquals(expected, clean);
    }

    /**
     * Link 태그가 escape 되는지 테스트
     */
    @Test
    public void linkAttackTest() {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-superset-sax.xml");
        String dirty = "<LINK REL=\"stylesheet\" HREF=\"javascript:alert('XSS');\">";
        String expected = "<!-- Not Allowed Tag Filtered -->&lt;LINK REL=\"stylesheet\" HREF=\"javascript:alert('XSS');\"&gt;";
        String clean = filter.doFilter(dirty);
        assertEquals(expected, clean);
    }

    /**
     * STYLE 속성에서 javascript 패턴 존재하는지 테스트
     */
    @Test
    public void styleAttackTest() {
        XssSaxFilter filter = XssSaxFilter.getInstance("lucy-xss-superset-sax.xml");
        String dirty = "<DIV STYLE=\"background-image: url(javascript:alert('XSS'))\">";
        String expected = "<!-- Not Allowed Attribute Filtered ( STYLE=\"background-image: url(javascript:alert('XSS'))\") --><DIV>";
        String clean = filter.doFilter(dirty);
        assertEquals(expected, clean);
    }


}
