package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;

/**
 * 게시글 상세 응답 DTO
 */
public record PostResponseDto(
        Long no,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        int views) {
    public static PostResponseDto from(Post entity) {
        if (entity == null)
            return null;
        return new PostResponseDto(
                entity.getNo(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getViews());
    }
}
