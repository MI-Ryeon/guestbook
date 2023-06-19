package com.sparta.guestbook.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.guestbook.entity.Guestbook;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuestbookResponseDto { // 변수 순서대로 출력
    private Boolean success;
    private Long id;
    private String title;
    private String username;
    private String post;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public GuestbookResponseDto(Guestbook guestbook) {
        this.id = guestbook.getId();
        this.title = guestbook.getTitle();
        this.username = guestbook.getUsername();
        this.post = guestbook.getPost();
        this.createdAt = guestbook.getCreateAt();
        this.modifiedAt = guestbook.getModifiedAt();
    }

    public GuestbookResponseDto(Boolean success) {
        this.success = success;
    }
}
