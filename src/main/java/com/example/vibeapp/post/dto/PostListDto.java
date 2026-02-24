package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import java.time.LocalDateTime;

/**
 * 게시글 목록 응답 DTO
 */
public class PostListDto {
    private Long no;
    private String title;
    private LocalDateTime createdAt;
    private int views;

    // Getters and Setters
    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public static PostListDto from(Post entity) {
        if (entity == null)
            return null;
        PostListDto dto = new PostListDto();
        dto.setNo(entity.getNo());
        dto.setTitle(entity.getTitle());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setViews(entity.getViews());
        return dto;
    }
}
