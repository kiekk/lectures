package io.security.corespringsecurity.controller.admin;

import io.security.corespringsecurity.domain.dto.AccountDto;
import io.security.corespringsecurity.domain.entity.Account;
import io.security.corespringsecurity.domain.entity.Role;
import io.security.corespringsecurity.service.RoleService;
import io.security.corespringsecurity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/accounts")
public class UserManagerController {

    private final UserService userService;

    private final RoleService roleService;

    public UserManagerController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getUsers(Model model) throws Exception {

        List<Account> accounts = userService.getUsers();
        model.addAttribute("accounts", accounts);

        return "admin/user/list";
    }

    @PostMapping("")
    public String modifyUser(AccountDto accountDto) throws Exception {

        userService.modifyUser(accountDto);

        return "redirect:/admin/accounts";
    }

    @GetMapping("{id}")
    public String getUser(@PathVariable(value = "id") Long id, Model model) {

        AccountDto accountDto = userService.getUser(id);
        List<Role> roleList = roleService.getRoles();

        model.addAttribute("account", accountDto);
        model.addAttribute("roleList", roleList);

        return "admin/user/detail";
    }

    @GetMapping("delete/{id}")
    public String removeUser(@PathVariable(value = "id") Long id, Model model) {

        userService.deleteUser(id);

        return "redirect:/admin/users";
    }
}