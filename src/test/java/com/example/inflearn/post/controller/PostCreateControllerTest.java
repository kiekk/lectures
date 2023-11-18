package com.example.inflearn.post.controller;

import com.example.inflearn.mock.TestContainer;
import com.example.inflearn.post.controller.response.PostResponse;
import com.example.inflearn.post.domain.PostCreate;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_작성할_수_있다() {
        // given
        final TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530673958L)
                .build();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build());

        final PostCreate postCreate = PostCreate.builder()
                .writerId(1L)
                .content("helloworld")
                .build();

        // when
        final PostResponse post = testContainer.postCreateController.createPost(postCreate);

        // then
        assertThat(post.getContent()).isEqualTo("helloworld");
        assertThat(post.getWriter().getNickname()).isEqualTo("soono");
        assertThat(post.getCreatedAt()).isEqualTo(1678530673958L);
    }
}
