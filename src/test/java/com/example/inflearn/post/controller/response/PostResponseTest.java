package com.example.inflearn.post.controller.response;

import com.example.inflearn.post.domain.Post;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostResponseTest {

    @Test
    @DisplayName("Post 객체로 응답을 생성할 수 있다.")
    void postResponseCreate() {
        // given
        final User writer = User.builder()
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        final Post post = Post.builder()
                .content("helloworld")
                .writer(writer)
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("shyoon991@gmail.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("soono");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
