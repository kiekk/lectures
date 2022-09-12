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

}
