package com.sparta.guestbook.dto;

import com.sparta.guestbook.entity.Guestbook;
import lombok.Getter;

@Getter
public class GuestbookResponseDto { // 변수 순서대로 출력
    private Long id;
    private String title;
    private String username;
    private String post;

    public GuestbookResponseDto(Guestbook guestbook) {
        this.id = guestbook.getId();
        this.title = guestbook.getTitle();
        this.username = guestbook.getUsername();
        this.post = guestbook.getPost();
    }

    public GuestbookResponseDto(Long id, String title, String username, String post, Long password) {
        this.id =id;
        this.title = title;
        this.username = username;
        this.post = post;
    }
}
