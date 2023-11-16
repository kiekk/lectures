package com.example.inflearn.service;

import com.example.inflearn.model.dto.post.PostCreateDto;
import com.example.inflearn.model.dto.post.PostUpdateDto;
import com.example.inflearn.repository.post.PostEntity;
import com.example.inflearn.service.post.PostService;
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
        final PostEntity postEntity = postService.getById(postId);

        // then
        assertThat(postEntity.getContent()).isEqualTo("helloworld");
        assertThat(postEntity.getWriter().getEmail()).isEqualTo("shyoon991@gmail.com");
    }

    @Test
    void postCreateDto를_이용하여_게시물을_생성할_수_있다() {
        // given
        final Long writerId = 1L;
        final PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(writerId)
                .content("foobar")
                .build();

        // when
        PostEntity postEntity = postService.create(postCreateDto);

        // then
        assertThat(postEntity.getId()).isNotNull();
        assertThat(postEntity.getContent()).isEqualTo("foobar");
        assertThat(postEntity.getWriter().getEmail()).isEqualTo("shyoon991@gmail.com");
        assertThat(postEntity.getCreatedAt()).isGreaterThan(0L);
    }

    @Test
    void postUpdateDto를_이용하여_게시물을_수정할_수_있다() {
        // given
        final Long postId = 1L;
        final PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("update content")
                .build();

        // when
        postService.update(postId, postUpdateDto);

        // then
        PostEntity postEntity = postService.getById(postId);
        assertThat(postEntity.getId()).isNotNull();
        assertThat(postEntity.getContent()).isEqualTo("update content");
        assertThat(postEntity.getWriter().getEmail()).isEqualTo("shyoon991@gmail.com");
        assertThat(postEntity.getCreatedAt()).isGreaterThan(0L);
        assertThat(postEntity.getModifiedAt()).isGreaterThan(0L);
    }
}
