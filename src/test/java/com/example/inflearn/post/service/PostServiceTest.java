package com.example.inflearn.post.service;

import com.example.inflearn.mock.FakePostRepository;
import com.example.inflearn.mock.FakeUserRepository;
import com.example.inflearn.mock.TestClockHolder;
import com.example.inflearn.post.controller.port.PostService;
import com.example.inflearn.post.domain.Post;
import com.example.inflearn.post.domain.PostCreate;
import com.example.inflearn.post.domain.PostUpdate;
import com.example.inflearn.user.domain.User;
import com.example.inflearn.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {

    private PostService postService;

    @BeforeEach
    void setUp() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.postService = PostServiceImpl.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .clockHolder(new TestClockHolder(1679530673958L))
                .build();
        User user1 = User.builder()
                .id(1L)
                .email("shyoon991@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .certificationCode("aaaa-aaa-aaaaab")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("shyoon992@gmail.com")
                .nickname("soono")
                .address("Seoul")
                .certificationCode("aaaa-aaa-aaaaabb")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build();
        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);
        fakePostRepository.save(Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(user1)
                .build());
    }

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
