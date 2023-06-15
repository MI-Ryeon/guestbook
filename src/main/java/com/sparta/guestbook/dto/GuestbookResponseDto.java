package com.sparta.guestbook.dto;

import com.sparta.guestbook.entity.Guestbook;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GuestbookResponseDto {
    private Long id;
    private String username;
    private String contents;
    private String title;
    private LocalDateTime createdDatetime;

    public GuestbookResponseDto(Guestbook guestbook) {
        this.id = guestbook.getId();
        this.username = guestbook.getUsername();
        this.contents = guestbook.getContents();
        this.title = guestbook.getTitle();
        this.createdDatetime = guestbook.getCreatedDatetime();
    }
}