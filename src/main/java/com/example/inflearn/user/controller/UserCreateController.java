package com.example.inflearn.user.controller;

import com.example.inflearn.user.controller.port.UserService;
import com.example.inflearn.user.controller.response.UserResponse;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserCreate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Builder
public class UserCreateController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserCreate userCreate) {
        User user = userService.create(userCreate);
        return UserResponse.from(user);
    }

}
