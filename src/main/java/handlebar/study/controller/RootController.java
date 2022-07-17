package handlebar.study.controller;

import handlebar.study.entity.User;
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
        return "home";
    }
}
