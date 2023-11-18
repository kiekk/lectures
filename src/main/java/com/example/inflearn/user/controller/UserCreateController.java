package com.example.inflearn.user.controller;

import com.example.inflearn.user.controller.port.UserCreateService;
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

    private final UserCreateService userCreateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserCreate userCreate) {
        User user = userCreateService.create(userCreate);
        return UserResponse.from(user);
    }

}
