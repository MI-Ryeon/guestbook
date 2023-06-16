package com.sparta.guestbook.dto;

import lombok.Getter;

@Getter
public class GuestbookRequestDto {
    private String title;
    private String username;
    private String post;
    private Long password;
}
