package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDto;
import com.example.vibeapp.post.dto.PostUpdateDto;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    public PostService(PostRepository postRepository, PostTagRepository postTagRepository) {
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
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
        String tags = joinTags(postTagRepository.findByPostNo(id));
        return PostResponseDto.from(refreshed, tags);
    }

    @Transactional
    public void create(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setViews(0);
        postRepository.save(post);

        saveTags(post.getNo(), createDto.tags());
    }

    @Transactional
    public void update(Long id, PostUpdateDto updateDto) {
        Post post = postRepository.findById(id);
        if (post == null) {
            return;
        }

        post.setTitle(updateDto.title());
        post.setContent(updateDto.content());
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.update(post);

        postTagRepository.deleteTagsByPostNo(id);
        saveTags(id, updateDto.tags());
    }

    public void delete(Long id) {
        postTagRepository.deleteTagsByPostNo(id);
        postRepository.deleteById(id);
    }

    private void saveTags(Long postNo, String tagsInput) {
        for (String tag : parseTags(tagsInput)) {
            PostTag postTag = new PostTag();
            postTag.setPostNo(postNo);
            postTag.setTagName(tag);
            postTagRepository.addTag(postTag);
        }
    }

    private List<String> parseTags(String tagsInput) {
        if (tagsInput == null || tagsInput.isBlank()) {
            return List.of();
        }
        return new LinkedHashSet<>(
                Arrays.stream(tagsInput.split(","))
                        .map(String::trim)
                        .filter(tag -> !tag.isBlank())
                        .toList())
                .stream()
                .toList();
    }

    private String joinTags(List<PostTag> tags) {
        if (tags == null || tags.isEmpty()) {
            return "";
        }
        return tags.stream()
                .map(PostTag::getTagName)
                .collect(Collectors.joining(", "));
    }
}
