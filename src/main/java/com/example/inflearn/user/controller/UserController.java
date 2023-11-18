package com.example.inflearn.user.controller;

import com.example.inflearn.user.controller.port.AuthenticationService;
import com.example.inflearn.user.controller.port.UserCreateService;
import com.example.inflearn.user.controller.port.UserReadService;
import com.example.inflearn.user.controller.port.UserUpdateService;
import com.example.inflearn.user.controller.response.MyProfileResponse;
import com.example.inflearn.user.controller.response.UserResponse;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserUpdate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Builder
public class UserController {

    private final UserCreateService userCreateService;
    private final UserUpdateService userUpdateService;
    private final UserReadService userReadService;
    private final AuthenticationService authenticationService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return UserResponse.from(userReadService.getById(id));
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(
            @PathVariable Long id,
            @RequestParam String certificationCode) {
        authenticationService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000"))
                .build();
    }

    @GetMapping("/me")
    public MyProfileResponse getMyInfo(@RequestHeader("EMAIL") String email) {
        User user = userReadService.getByEmail(email);
        authenticationService.login(user.getId());
        return MyProfileResponse.from(userReadService.getById(user.getId()));
    }

    @PutMapping("/me")
    public MyProfileResponse updateMyInfo(
            @RequestHeader("EMAIL") String email,
            @RequestBody UserUpdate userUpdate
    ) {
        User user = userReadService.getByEmail(email);
        user = userUpdateService.update(user.getId(), userUpdate);
        return MyProfileResponse.from(user);
    }

}
