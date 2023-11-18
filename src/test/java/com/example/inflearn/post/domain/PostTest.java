package com.example.inflearn.post.domain;

import com.example.inflearn.mock.TestClockHolder;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostTest {

    @Test
    @DisplayName("PostCreate로 게시물을 만들 수 있다.")
    void postCreate() {
        // given
        final PostCreate postCreate = PostCreate.builder()
                .writerId(1L)
                .content("helloworld")
                .build();

        final User writer = User.builder()
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaa-aaa-aaaa")
                .build();

        // when
        final Post post = Post.from(postCreate, writer, new TestClockHolder(1678530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("helloworld");
        assertThat(post.getWriter().getEmail()).isEqualTo("shyoon991@gmail.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("soono");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaa-aaa-aaaa");
    }

    @Test
    @DisplayName("PostUpdate로 게시물을 수정할 수 있다.")
    void postUpdate() {
        // given
        final PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();
        final User writer = User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();
        final Post post = Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(writer)
                .build();

        // when
        final Post updatedPost = post.update(postUpdate, new TestClockHolder(1679530673958L));

        // then
        assertThat(updatedPost.getContent()).isEqualTo("foobar");
        assertThat(updatedPost.getModifiedAt()).isEqualTo(1679530673958L);
    }

}
