package com.example.inflearn.post.infrastructure;

import com.example.inflearn.post.domain.Post;
import com.example.inflearn.post.service.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id).map(PostEntity::to);
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).to();
    }
}
