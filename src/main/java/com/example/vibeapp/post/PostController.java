package com.example.vibeapp.post;

import com.example.vibeapp.post.dto.PostCreateDto;
import com.example.vibeapp.post.dto.PostListDto;
import com.example.vibeapp.post.dto.PostResponseDto;
import com.example.vibeapp.post.dto.PostUpdateDto;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<PostPageResponse> listPosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        List<PostListDto> posts = postService.findPagedPosts(page, size);
        int totalPages = postService.getTotalPages(size);
        return ResponseEntity.ok(new PostPageResponse(posts, page, size, totalPages));
    }

    @GetMapping("/{no}")
    public ResponseEntity<PostResponseDto> getPostDetail(@PathVariable("no") Long no) {
        PostResponseDto post = postService.findById(no);
        if (post == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + no);
        }
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> addPost(@Valid @RequestBody PostCreateDto createDto) {
        Long newId = postService.create(createDto);
        PostResponseDto created = postService.findByIdWithoutIncrease(newId);
        return ResponseEntity.created(URI.create("/api/posts/" + newId)).body(created);
    }

    @PatchMapping("/{no}")
    public ResponseEntity<PostResponseDto> savePost(
            @PathVariable("no") Long no,
            @Valid @RequestBody PostUpdateDto updateDto) {
        postService.update(no, updateDto);
        PostResponseDto updated = postService.findByIdWithoutIncrease(no);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deletePost(@PathVariable("no") Long no) {
        postService.delete(no);
        return ResponseEntity.noContent().build();
    }

    public record PostPageResponse(
            List<PostListDto> items,
            int page,
            int size,
            int totalPages) {
    }
}
