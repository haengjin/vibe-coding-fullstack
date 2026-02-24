package com.example.vibeapp.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 게시글 수정 요청 DTO
 */
public record PostUpdateDto(
        @NotBlank(message = "제목은 필수 입력 사항입니다.") @Size(max = 100, message = "제목은 100자 이내여야 합니다.") String title,
        String content) {
}
