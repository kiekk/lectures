package com.example.inflearn.controller.user;

import com.example.inflearn.model.dto.user.UserCreateDto;
import com.example.inflearn.model.dto.user.UserResponse;
import com.example.inflearn.repository.user.UserEntity;
import com.example.inflearn.service.user.UserService;
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
    public UserResponse createUser(@RequestBody UserCreateDto userCreateDto) {
        UserEntity userEntity = userService.create(userCreateDto);
        return userController.toResponse(userEntity);
    }

}
