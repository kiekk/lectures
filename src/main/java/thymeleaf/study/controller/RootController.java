package thymeleaf.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import thymeleaf.study.entity.User;

import java.util.*;

@Controller
@RequestMapping("")
public class RootController {

    @RequestMapping({"", "/"})
    public String root(Model model) {
        User user = new User();
        user.setId("id_1");
        user.setName("홍길동");
        user.setMobile("010-1234-5678");

        User user2 = new User();
        user2.setId("id_2");
        user2.setName("test");
        user2.setMobile("010-1234-5678");

        User user3 = new User();
        user3.setId("id_3");
        user3.setName("test3");
        user3.setMobile(null);

        model.addAttribute("message", "hello thymeleaf");
        model.addAttribute("user", user);
        model.addAttribute("user2", user2);
        model.addAttribute("user3", user3);
        return "main";
    }

    @RequestMapping("each")
    public String each(Model model) {
        List<User> userList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i + "");
            user.setName("name_" + i);
            user.setMobile("010-1111-1234");
            userList.add(user);
        }

        model.addAttribute("userList", userList);
        return "each";
    }

    @RequestMapping("with")
    public String with(Model model) {
        List<User> userList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i + "");
            user.setName("name_" + i);
            user.setMobile("010-1111-1234");
            userList.add(user);
        }

        model.addAttribute("userList", userList);
        return "with";
    }

    @RequestMapping("object")
    public String object(Model model) {
        User user = new User();
        user.setId("id_1");
        user.setName("홍길동");
        user.setMobile("010-1234-5678");
        model.addAttribute("user", user);
        return "object";
    }

    @RequestMapping("value")
    public String value(Model model) {
        User user = new User();
        user.setId("id_1");
        user.setName("홍길동");
        user.setMobile("010-1234-5678");
        model.addAttribute("user", user);
        return "value";
    }

    @RequestMapping("text")
    public String text(Model model) {
        String html = "<div><span>html tag</span></div>";

        model.addAttribute("html", html);
        return "text";
    }

    @RequestMapping("url")
    public String url(Model model) {
        model.addAttribute("textUrl", "text");
        return "url";
    }

    @RequestMapping("case")
    public String switchCase(Model model) {
        model.addAttribute("case", "a");
        return "case";
    }

    @RequestMapping("string-concat")
    public String stringConcat(Model model) {
        model.addAttribute("one", 1);
        model.addAttribute("two", 2);
        model.addAttribute("three", 3);

        model.addAttribute("username", "홍길동");
        return "string-concat";
    }

    @RequestMapping("messages")
    public String messages(Model model) {

        model.addAttribute("name", "foo");
        return "messages";
    }

    @RequestMapping("block")
    public String block(Model model) {
        List<User> userList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i + "");
            user.setName("name_" + i);
            user.setMobile("010-1111-1234");
            userList.add(user);
        }

        model.addAttribute("userList", userList);
        return "block";
    }

    @RequestMapping("fragment")
    public String fragment(Model model) {
        User user = new User();
        user.setId("id_1");
        user.setName("홍길동");
        user.setMobile("010-1234-5678");
        model.addAttribute("user", user);
        return "fragment";
    }

    @RequestMapping("layout-test-1")
    public String layoutTest1() {
        return "layout-test-1";
    }

    @RequestMapping("layout-test-2")
    public String layoutTest2() {
        return "layout-test-2";
    }

    @RequestMapping("pre-processing")
    public String preProcessing(Model model) {
        Map<String, Object> result = new HashMap<>();
        User user = new User();
        result.put("data1", "data2");
        result.put("data2", user);

        user.setId("id_1");
        user.setName("홍길동");
        user.setMobile("010-1234-5678");
        model.addAttribute("result", result);
        model.addAttribute("data", "data1");
        return "pre-processing";
    }

    @RequestMapping("basic-objects")
    public String basicObjects(Model model) {
        return "basic-objects";
    }

    @RequestMapping("util-objects")
    public String utilObjects(Model model) {
        List<String> list = List.of("list1", "list2", "list3");
        Set<String> set = Set.of("set1", "set2", "set3");
        String[] array = {"array1", "array2", "array3"};
        model.addAttribute("list", list);
        model.addAttribute("set", set);
        model.addAttribute("array", array);
        model.addAttribute("param1", "param1");
        model.addAttribute("param2", "param2");
        model.addAttribute("param3", "param3");
        model.addAttribute("uri", "https://www.naver.com?param1=param2&param2=param2");
        model.addAttribute("encoding", "UTF-8");
        model.addAttribute("object", new Date().getTime());
        model.addAttribute("targetClass", java.util.Date.class);
        model.addAttribute("date", new Date());
        model.addAttribute("dateSet", Set.of(new Date(2022, Calendar.AUGUST, 11), new Date(2022, Calendar.AUGUST, 12), new Date(2022, Calendar.AUGUST, 13)));
        model.addAttribute("dateList", List.of(new Date(), new Date(), new Date()));
        model.addAttribute("dateArray", new Date[]{new Date(), new Date(), new Date()});
        model.addAttribute("year", "2022");
        model.addAttribute("month", "9");
        model.addAttribute("day", "17");
        model.addAttribute("hour", "10");
        model.addAttribute("minute", "30");
        model.addAttribute("second", "30");
        model.addAttribute("millisecond", "20");
        model.addAttribute("timeZone", "Asia/Seoul");
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2022, Calendar.AUGUST, 11);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2022, Calendar.AUGUST, 12);
        Calendar cal3 = Calendar.getInstance();
        cal3.set(2022, Calendar.AUGUST, 13);

        model.addAttribute("cal", cal1);
        model.addAttribute("calArray", new Calendar[]{cal1, cal2, cal3});
        model.addAttribute("calList", List.of(cal1, cal2, cal3));
        model.addAttribute("calSet", Set.of(cal1, cal2, cal3));

        model.addAttribute("num", 2);
        model.addAttribute("numArray", new Integer[]{1000, 2000, 3000});
        model.addAttribute("numList", List.of(1000, 2000, 3000));
        model.addAttribute("numSet", Set.of(1000, 2000, 3000));

        model.addAttribute("from", 2);
        model.addAttribute("to", 10);
        model.addAttribute("step", 2);

        User user = new User();

        user.setId("id_1");
        user.setName("홍길동");
        user.setMobile("010-1234-5678");
        model.addAttribute("obj", user);
        model.addAttribute("name", "name1");
        model.addAttribute("nameArr", new String[]{"name1", "name2", "name3"});
        model.addAttribute("nameList", List.of("name1", "name2", "name3"));
        model.addAttribute("nameSet", Set.of("name1", "name2", "name3"));
        model.addAttribute("text", "text");
        model.addAttribute("textArr", new String[]{"text1", "text2", ""});
        model.addAttribute("textList", List.of("text1", "text2", ""));
        model.addAttribute("textSet", Set.of("text1", "text2", ""));
        model.addAttribute("default", "default text");
        model.addAttribute("prefix", "_prefix");
        model.addAttribute("suffix", "_suffix");
        model.addAttribute("str", " str text");
        model.addAttribute("obj", null);
        model.addAttribute("objArray", new User[]{new User(), user});
        model.addAttribute("objList", List.of(new User(), user));
        model.addAttribute("objSet", Set.of(new User(), user));
        model.addAttribute("default", "default object");
        model.addAttribute("condArray", new Boolean[]{false, true});
        model.addAttribute("condList", List.of(false, true, false));
        model.addAttribute("condSet", Set.of(false, true));
        return "util-objects";
    }

}
