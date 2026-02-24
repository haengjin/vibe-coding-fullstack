package com.example.vibeapp.post.dto;

import com.example.vibeapp.post.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateDto(
        @NotBlank(message = "제목은 필수 입력값입니다.")
        @Size(max = 100, message = "제목은 100자 이하여야 합니다.")
        String title,
        String content,
        String tags) {

    public Post toEntity() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setContent(this.content == null ? "" : this.content);
        return post;
    }
}
