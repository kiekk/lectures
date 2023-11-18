package com.example.inflearn.user.controller;

import com.example.inflearn.user.controller.response.UserResponse;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserCreate;
import com.example.inflearn.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCreateController {

    private final UserServiceImpl userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserCreate userCreate) {
        User user = userService.create(userCreate);
        return UserResponse.from(user);
    }

}
