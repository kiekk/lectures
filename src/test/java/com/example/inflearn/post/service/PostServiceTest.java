package com.example.inflearn.post.service;

import com.example.inflearn.post.domain.Post;
import com.example.inflearn.post.domain.PostCreate;
import com.example.inflearn.post.domain.PostUpdate;
import com.example.inflearn.post.infrastructure.PostEntity;
import com.example.inflearn.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void getById는_존재하는_게시물을_내려준다() {
        // given
        final Long postId = 1L;

        // when
        final Post post = postService.getById(postId);

        // then
        assertThat(post.getContent()).isEqualTo("helloworld");
        assertThat(post.getWriter().getEmail()).isEqualTo("shyoon991@gmail.com");
    }

    @Test
    void postCreateDto를_이용하여_게시물을_생성할_수_있다() {
        // given
        final Long writerId = 1L;
        final PostCreate postCreate = PostCreate.builder()
                .writerId(writerId)
                .content("foobar")
                .build();

        // when
        final Post post = postService.create(postCreate);

        // then
        assertThat(post.getId()).isNotNull();
        assertThat(post.getContent()).isEqualTo("foobar");
        assertThat(post.getWriter().getEmail()).isEqualTo("shyoon991@gmail.com");
        assertThat(post.getCreatedAt()).isGreaterThan(0L);
    }

    @Test
    void postUpdateDto를_이용하여_게시물을_수정할_수_있다() {
        // given
        final Long postId = 1L;
        final PostUpdate postUpdate = PostUpdate.builder()
                .content("update content")
                .build();

        // when
        postService.update(postId, postUpdate);

        // then
        final Post post = postService.getById(postId);

        assertThat(post.getId()).isNotNull();
        assertThat(post.getContent()).isEqualTo("update content");
        assertThat(post.getWriter().getEmail()).isEqualTo("shyoon991@gmail.com");
        assertThat(post.getCreatedAt()).isGreaterThan(0L);
        assertThat(post.getModifiedAt()).isGreaterThan(0L);
    }
}
