package com.example.inflearn.user.controller;

import com.example.inflearn.user.controller.port.UserService;
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

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id) {
        return UserResponse.from(userService.getById(id));
    }

    @GetMapping("/{id}/verify")
    public ResponseEntity<Void> verifyEmail(
            @PathVariable Long id,
            @RequestParam String certificationCode) {
        userService.verifyEmail(id, certificationCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:3000"))
                .build();
    }

    @GetMapping("/me")
    public MyProfileResponse getMyInfo(@RequestHeader("EMAIL") String email) {
        User user = userService.getByEmail(email);
        userService.login(user.getId());
        return MyProfileResponse.from(userService.getById(user.getId()));
    }

    @PutMapping("/me")
    public MyProfileResponse update(
            @RequestHeader("EMAIL") String email,
            @RequestBody UserUpdate userUpdate
    ) {
        User user = userService.getByEmail(email);
        user = userService.update(user.getId(), userUpdate);
        return MyProfileResponse.from(user);
    }

}
