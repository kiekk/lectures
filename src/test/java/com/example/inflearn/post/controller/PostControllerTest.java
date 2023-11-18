package com.example.inflearn.post.controller;

import com.example.inflearn.common.domain.exception.ResourceNotFoundException;
import com.example.inflearn.mock.TestContainer;
import com.example.inflearn.post.controller.response.PostResponse;
import com.example.inflearn.post.domain.Post;
import com.example.inflearn.post.domain.PostUpdate;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostControllerTest {

    @Test
    void 사용자는_게시물을_단건_조회_할_수_있다() {
        final TestContainer testContainer = TestContainer.builder()
                .build();
        final User user = User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaa-aaa-aaaa")
                .lastLoginAt(100L)
                .build();
        testContainer.userRepository.save(user);
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("helloworld")
                .writer(user)
                .createdAt(100L)
                .build());

        // when
        final PostResponse postResponse = testContainer.postController.getById(1L);

        // then
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("soono");
        assertThat(postResponse.getCreatedAt()).isEqualTo(100L);
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() {
        // given
        TestContainer testContainer = TestContainer.builder()
                .build();

        // when
        // then
        assertThatThrownBy(() -> testContainer.postController.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_게시물을_수정할_수_있다() {
        // given
        final TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 200L)
                .build();
        final User user = User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaa-aaa-aaaa")
                .lastLoginAt(100L)
                .build();
        testContainer.userRepository.save(user);
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("helloworld")
                .writer(user)
                .createdAt(100L)
                .build());

        // when
        final PostResponse postResponse = testContainer.postController.update(1L, PostUpdate.builder()
                .content("foobar")
                .build());

        // then
        assertThat(postResponse.getContent()).isEqualTo("foobar");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("soono");
        assertThat(postResponse.getCreatedAt()).isEqualTo(100L);
        assertThat(postResponse.getModifiedAt()).isEqualTo(200L);
    }
}
