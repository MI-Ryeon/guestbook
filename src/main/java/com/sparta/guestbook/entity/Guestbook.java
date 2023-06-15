package com.sparta.guestbook.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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
    private Long password;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdDatetime = LocalDateTime.now();

    public Guestbook(GuestbookRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
        this.createdDatetime = requestDto.getCreatedDatetime();
    }

    public void update(GuestbookRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.password = requestDto.getPassword();
        this.createdDatetime = requestDto.getCreatedDatetime();
    }
}
