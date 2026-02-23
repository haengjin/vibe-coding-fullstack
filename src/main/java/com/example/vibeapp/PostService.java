package com.example.vibeapp;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    private long nextNo = 11; // 초기 데이터 10개 이후부터 시작

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 10; i++) {
            Post post = new Post(
                    (long) i,
                    "테스트 게시글 제목 " + i,
                    "테스트 게시글 내용입니다. " + i,
                    LocalDateTime.now().minusDays(10 - i),
                    LocalDateTime.now().minusDays(10 - i),
                    i * 5);
            postRepository.save(post);
        }
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostByNo(Long no) {
        Post post = postRepository.findByNo(no).orElse(null);
        if (post != null) {
            post.setViews(post.getViews() + 1);
        }
        return post;
    }

    public void addPost(String title, String content) {
        Post post = new Post();
        post.setNo(nextNo++);
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.save(post);
    }
}
