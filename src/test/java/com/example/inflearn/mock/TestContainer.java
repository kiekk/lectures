package com.example.inflearn.mock;

import com.example.inflearn.common.service.port.ClockHolder;
import com.example.inflearn.common.service.port.UuidHolder;
import com.example.inflearn.post.controller.PostController;
import com.example.inflearn.post.controller.PostCreateController;
import com.example.inflearn.post.controller.port.PostService;
import com.example.inflearn.post.service.PostServiceImpl;
import com.example.inflearn.post.service.port.PostRepository;
import com.example.inflearn.user.controller.UserController;
import com.example.inflearn.user.controller.UserCreateController;
import com.example.inflearn.user.controller.port.AuthenticationService;
import com.example.inflearn.user.controller.port.UserCreateService;
import com.example.inflearn.user.controller.port.UserReadService;
import com.example.inflearn.user.controller.port.UserUpdateService;
import com.example.inflearn.user.service.CertificationService;
import com.example.inflearn.user.service.UserServiceImpl;
import com.example.inflearn.user.service.port.MailSender;
import com.example.inflearn.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {
    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final PostService postService;
    public final UserReadService userReadService;
    public final UserCreateService userCreateService;
    public final UserUpdateService userUpdateService;
    public final AuthenticationService authenticationService;
    public final CertificationService certificationService;
    public final UserController userController;
    public final UserCreateController userCreateController;
    public final PostController postController;
    public final PostCreateController postCreateController;

    @Builder
    public TestContainer(ClockHolder clockHolder,
                         UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.postService = PostServiceImpl.builder()
                .postRepository(this.postRepository)
                .userRepository(this.userRepository)
                .clockHolder(clockHolder)
                .build();
        this.certificationService = new CertificationService(this.mailSender);
        UserServiceImpl userService = UserServiceImpl.builder()
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .userRepository(this.userRepository)
                .certificationService(this.certificationService)
                .build();

        this.userReadService = userService;
        this.userCreateService = userService;
        this.userUpdateService = userService;
        this.authenticationService = userService;
        this.userController = UserController.builder()
                .userReadService(userReadService)
                .userCreateService(userCreateService)
                .userUpdateService(userUpdateService)
                .authenticationService(authenticationService)
                .build();
        this.userCreateController = UserCreateController.builder()
                .userCreateService(userCreateService)
                .build();
        this.postController = PostController.builder()
                .postService(postService)
                .build();
        this.postCreateController = PostCreateController.builder()
                .postService(postService)
                .build();
    }

}
