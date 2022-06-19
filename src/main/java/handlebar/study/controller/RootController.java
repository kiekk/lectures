package handlebar.study.controller;

import handlebar.study.entity.User;
import handlebar.study.entity.UserDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class RootController {

    @RequestMapping("")
    public String root(Model model) {
        User user = new User();
        user.setId("id_1");
        user.setName("name_1");
        user.setAge(10);

        model.addAttribute("hello", "hello handlebars");
        model.addAttribute("user", user);
        model.addAttribute("name", "root name");
        return "home";
    }

    @RequestMapping("comment")
    public String comment(Model model) {
        model.addAttribute("messages", "hello handlebars");
        return "comment";
    }

    @RequestMapping("with")
    public String with(Model model) {
        User user = new User();
        user.setId("id_1");
        user.setName("name_1");
        user.setAge(10);

        UserDetail detail = new UserDetail();
        detail.setAddress("주소_1");
        detail.setEmail("이메일_1");
        detail.setMobile("전화번호_1");

        user.setDetail(detail);

        User user2 = new User();
        user2.setId("id_2");
        user2.setName("name_2");
        user2.setAge(20);

        model.addAttribute("user", user);
        model.addAttribute("user2", user2);
        return "with";
    }
}
