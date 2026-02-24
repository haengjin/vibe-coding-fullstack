package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDto;
import com.example.vibeapp.post.dto.PostUpdateDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostListDto> findPagedPosts(int page, int size) {
        int offset = Math.max(page - 1, 0) * size;
        return postRepository.findPaged(offset, size).stream()
                .map(PostListDto::from)
                .collect(Collectors.toList());
    }

    public int getTotalPages(int size) {
        int totalPosts = postRepository.countAll().intValue();
        return (int) Math.ceil((double) totalPosts / size);
    }

    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id);
        if (post == null) {
            return null;
        }

        postRepository.increaseViews(id);
        Post refreshed = postRepository.findById(id);
        return PostResponseDto.from(refreshed);
    }

    public void create(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.save(post);
    }

    public void update(Long id, PostUpdateDto updateDto) {
        Post post = postRepository.findById(id);
        if (post != null) {
            post.setTitle(updateDto.title());
            post.setContent(updateDto.content());
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.update(post);
        }
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
