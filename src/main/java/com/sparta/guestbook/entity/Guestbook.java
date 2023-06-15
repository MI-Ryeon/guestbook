package com.sparta.guestbook.entity;

import com.sparta.guestbook.dto.GuestbookRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Guestbook {
    private Long id;
    private String username;
    private String contents;
    private String title;
    private LocalDateTime createdDatetime;
    private Long password;

    public Guestbook(GuestbookRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
    }
}
