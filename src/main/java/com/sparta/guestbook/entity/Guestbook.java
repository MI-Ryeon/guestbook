package com.sparta.guestbook.entity;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Guestbook {
    private Long id;
    private String title;
    private String username;
    private String post;
    private Long password;

    public Guestbook(GuestbookRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.post = requestDto.getPost();
        this.password = requestDto.getPassword();
    }

    public void update(GuestbookRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.post = requestDto.getPost();
        this.password = requestDto.getPassword();
    }
}
