package thymeleaf.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import thymeleaf.study.entity.User;

import java.util.ArrayList;
import java.util.List;

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

        model.addAttribute("message", "hello thymeleaf");
        model.addAttribute("user", user);
        model.addAttribute("user2", user2);
        return "main";
    }

    @RequestMapping("each")
    public String each(Model model) {
        List<User> userList = new ArrayList<>();

        for(int i=0;i<10;i++) {
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

        for(int i=0;i<10;i++) {
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
}
