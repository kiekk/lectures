package com.example.inflearn.user.controller;

import com.example.inflearn.user.domain.UserCreate;
import com.example.inflearn.user.controller.response.UserResponse;
import com.example.inflearn.user.infrastructure.UserEntity;
import com.example.inflearn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCreateController {

    private final UserController userController;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserCreate userCreate) {
        UserEntity userEntity = userService.create(userCreate);
        return userController.toResponse(userEntity);
    }

}
