package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 게시글 생성 요청 DTO
 */
public class PostCreateDto {

    @NotBlank(message = "제목은 필수 입력 사항입니다.")
    @Size(max = 100, message = "제목은 100자 이내여야 합니다.")
    private String title;

    private String content;

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post toEntity() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setContent(this.content);
        return post;
    }
}
