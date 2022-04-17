package io.security.corespringsecurity.controller.user;

import io.security.corespringsecurity.domain.entity.Account;
import io.security.corespringsecurity.domain.dto.AccountDto;
import io.security.corespringsecurity.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/my-page")
    public String myPage() {
        return "user/my-page";
    }

    @GetMapping("/users")
    public String createUser() {
        return "/user/login/register";
    }

    @PostMapping("/users")
    public String createUser(AccountDto accountDto) {

        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        userService.createUser(account);

        return "redirect:/";
    }
}
