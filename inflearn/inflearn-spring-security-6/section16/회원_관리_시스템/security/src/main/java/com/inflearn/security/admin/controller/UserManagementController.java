package com.inflearn.security.admin.controller;

import com.inflearn.security.admin.service.RoleService;
import com.inflearn.security.admin.service.UserManagementService;
import com.inflearn.security.domain.dto.AccountDto;
import com.inflearn.security.domain.entity.Account;
import com.inflearn.security.domain.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserManagementController {
    private final UserManagementService userManagementService;
    private final RoleService roleService;

    @GetMapping(value = "/admin/users")
    public String getUsers(Model model) {

        List<Account> users = userManagementService.getUsers();
        model.addAttribute("users", users);

        return "admin/users";
    }

    @PostMapping(value = "/admin/users")
    public String modifyUser(AccountDto accountDto) {

        userManagementService.modifyUser(accountDto);

        return "redirect:/admin/users";
    }

    @GetMapping(value = "/admin/users/{id}")
    public String getUser(@PathVariable(value = "id") Long id, Model model) {

        AccountDto accountDto = userManagementService.getUser(id);
        List<Role> roleList = roleService.getRolesWithoutExpression();

        model.addAttribute("user", accountDto);
        model.addAttribute("roleList", roleList);

        return "admin/userdetails";
    }

    @GetMapping(value = "/admin/users/delete/{id}")
    public String removeUser(@PathVariable(value = "id") Long id) {

        userManagementService.deleteUser(id);

        return "redirect:admin/users";
    }
}
