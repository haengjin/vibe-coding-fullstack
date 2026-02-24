package com.example.vibeapp.post;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<Post> findAll() {
        return postRepository.findAll().stream()
                .sorted(Comparator.comparing(Post::getNo).reversed())
                .collect(Collectors.toList());
    }

    public List<Post> findPagedPosts(int page, int size) {
        List<Post> allPosts = findAll();
        int start = (page - 1) * size;
        int end = Math.min(start + size, allPosts.size());

        if (start > allPosts.size()) {
            return List.of();
        }
        return allPosts.subList(start, end);
    }

    public int getTotalPages(int size) {
        int totalPosts = postRepository.findAll().size();
        return (int) Math.ceil((double) totalPosts / size);
    }

    public Post findById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setViews(post.getViews() + 1);
        }
        return post;
    }

    public void create(String title, String content) {
        Post post = new Post();
        post.setNo(nextNo++);
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.save(post);
    }

    public void update(Long id, String title, String content) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            post.setUpdatedAt(LocalDateTime.now());
        }
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
